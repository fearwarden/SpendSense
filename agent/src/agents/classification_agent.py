import asyncio
import json

from langchain.memory.entity import BaseEntityStore
from langchain_core.messages import HumanMessage, SystemMessage
from langchain_core.prompts import ChatPromptTemplate
from langchain_tavily import TavilySearch
from langgraph.prebuilt import create_react_agent
from pydantic import BaseModel, Field

from src.chat_client import chat

class Classification(BaseModel):
    category: str = Field(description="Classified category")
    subcategory: str = Field(description="Classified subcategory")

classification_prompt = """
    You are a financial transaction classifier. Your task is to classify transactions into a category and subcategory using the following output structure (exact JSON):

    {"category": "string", "subcategory": "string|null"}
    
    Categories and Subcategories:
    - ESSENTIALS
      - GROCERIES
      - UTILITIES
      - PHARMACY
    - TRANSPORTATION
      - PUBLIC_TRANSPORT
      - FUEL
      - TAXI
    - LEISURE
      - RESTAURANTS_TAKEAWAY
      - ENTERTAINMENT
      - SHOPPING
    - TRAVEL
    - SUBSCRIPTIONS
    - MISC
    - INCOME
    
    Fields available for classification include (but are not limited to):
    moneyAccountName, currencyId, currencyName, accountType, product, customerName,
    transactionId, transactionTypeId, transactionTypeShort, transactionTypeName,
    bookingTypeShort, bookingTypeName, valueDate, transactionDate, direction,
    amount, transactionCurrencyId, transactionCurrencyName, creditorShortText,
    creditorText, debtorShortText, debtorText, pointOfSale, acquirerCountryId,
    acquirerCountryName, cardId, creditorAccountText, creditorIBAN, creditorAddress,
    creditorReferenceNumber, creditorInfo
    
    Rules:
    1. If \"direction\" == \"1\", always return:
       {\"category\": \"Income\", \"subcategory\": \"null\"}
    
    2. Otherwise, classify using available fields (merchant / pointOfSale / creditorText / debtorText / transactionTypeName / bookingTypeName / amount / bookingTypeShort etc.). Prefer merchant and POS information for retail classification (e.g., bakery, supermarket, petrol station, taxi company).
    
    3. If a merchant name or descriptor is ambiguous or unknown, you MAY perform an external search to disambiguate the merchant and refine the classification. Use an external lookup only when necessary. If you do use external sources, prefer reliable information about the merchant (merchant category, business type, website, Google Maps listing, etc.).
    
    4. If after checking available fields (and performing a search if needed) you are still unsure, classify as:
       {\"category\": \"Misc\", \"subcategory\": \"null\"}
    
    5. Do NOT ask follow-up questions. Always respond in the exact JSON format specified above and nothing else.
    
    6. When possible pick the most specific subcategory (for example: a supermarket -> Essentials / Groceries; a bakery cafe -> Leisure / Restaurants / Takeaway unless evidence indicates grocery purchase).
    
    7. Edge cases:
       - Recurring payments to known services -> Subscriptions
       - Refunds or reversals with direction == 1 -> Income
       - Large transfers labelled as salary, payroll, stipend, pension -> Income
       - Travel agencies, airlines, hotels -> Travel
       - App stores / streaming service merchants -> Subscriptions (or Misc if ambiguous)
    
    Example output formats (must match exactly):
    {"category": "Income", "subcategory": ""}
    {"category": "Essentials", "subcategory": "Groceries"}
    {"category": "Leisure", "subcategory": "Restaurants / Takeaway"}
    {"category": "Misc", "subcategory": ""}
    """

agent = create_react_agent(
    chat,
    tools=[TavilySearch(max_results=1)],
    response_format=Classification,
    prompt=classification_prompt,
)

async def classify(transaction):
    human_message = HumanMessage(transaction)
    system_message = SystemMessage(transaction)

    resp = await agent.ainvoke(
        {
            "messages": [system_message, human_message],
        }
    )

    return resp["structured_response"]
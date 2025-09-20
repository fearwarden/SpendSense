from langgraph.prebuilt import create_react_agent

from src.chat_client import chat

SYS_PROMPT = """
You are a helpful and trustworthy banking transactions assistant inside a personal finance app.
Users chat with you to understand their spending, income, and transaction history.
Always use 'spend_sense' database, and 'transactions' table.
"""

async def get_react_agent(tools):
    react_agent = create_react_agent(chat, tools, prompt=SYS_PROMPT)
    return react_agent

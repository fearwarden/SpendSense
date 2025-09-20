from contextlib import asynccontextmanager

from fastapi import FastAPI, Request
from pydantic import BaseModel

from src.agents.classification_agent import classify
from src.models import chat_model
from src.mcp_client import get_tools

@asynccontextmanager
async def lifespan(app: FastAPI):
    try:
        app.state.tools = await get_tools()
    except:
        raise Exception("Loading MCP server failed!")
    print("MCP tools loaded:", app.state.tools)

    yield

    print("Shutting down MCP server")

app = FastAPI(lifespan=lifespan)

class Message(BaseModel):
    content: str
    thread_id: str

class ClassificationDto(BaseModel):
    transaction: str

@app.post("/api/chat/message")
async def chat(message: Message, request: Request):
    tools = request.app.state.tools
    return await chat_model.chat(message.content, message.thread_id, tools)

@app.post("/api/chat/classification")
async def classification(body: ClassificationDto):
    response = await classify(body.transaction)
    return response

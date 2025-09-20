from contextlib import asynccontextmanager

from fastapi import FastAPI, Request
from pydantic import BaseModel
from src.models import chat_model
from src.mcp_client import get_tools

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup
    try:
        app.state.tools = await get_tools()
    except:
        raise "BORIS"
    print("MCP tools loaded:", app.state.tools)

    yield  # 👈 app runs here

    # Shutdown
    # If you need cleanup, put it here
    print("Shutting down MCP server")

app = FastAPI(lifespan=lifespan)

class Message(BaseModel):
    content: str

@app.post("/api/chat/message")
async def chat(message: Message, request: Request):
    tools = request.app.state.tools
    response = await chat_model.chat(message.content, tools)
    return {"message": response}
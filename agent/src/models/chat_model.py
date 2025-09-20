import json

from langchain_core.runnables import RunnableConfig
from fastapi.responses import StreamingResponse

from src.agents.react_agent import get_react_agent
from src.tracing.tracing import callback_handler, get_tracing_metadata

async def chat(msg: str, thread_id: str, tools):
    react_agent = await get_react_agent(tools)
    config = RunnableConfig(recursion_limit=25, callbacks=[callback_handler], metadata=get_tracing_metadata(), configurable={"thread_id": thread_id})

    async def event_stream():
        async for token, metadata in react_agent.astream(
                {"messages": [{"role": "user", "content": msg}]},
                stream_mode="messages",
                config=config,
        ):
            content = token.content
            if not (hasattr(token, "name") and token.name) and content:
                obj = {"token": token.content}
                yield f"data: {json.dumps(obj)}\n\n"

    return StreamingResponse(event_stream(), media_type="text/event-stream")
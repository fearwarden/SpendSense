from langchain_core.messages import HumanMessage
from langchain_core.runnables import RunnableConfig

from src.agents.react_agent import get_react_agent
from src.tracing.tracing import callback_handler, get_tracing_metadata

async def chat(msg: str, tools):
    react_agent = await get_react_agent(tools)
    thread_id = "conversation-1"
    agent_config = {"configurable": {"thread_id": thread_id}}
    config = RunnableConfig(recursion_limit=25, callbacks=[callback_handler], metadata=get_tracing_metadata(), configurable={"thread_id": thread_id})
    response = await react_agent.ainvoke({"messages": [HumanMessage(msg)]}, config=config)
    content = response.get("messages")[-1].content
    return content
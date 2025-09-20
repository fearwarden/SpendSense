from langchain_core.messages import HumanMessage

from src.agents.react_agent import get_react_agent

async def chat(msg: str, tools):
    react_agent = await get_react_agent(tools)
    response = react_agent.invoke({"messages": [HumanMessage(msg)]})
    return response.get("messages")[-1].content
from langchain_mcp_adapters.client import MultiServerMCPClient

from src.env import DB_HOST, DB_USER, DB_PASSWORD, DB_PORT, DB_NAME, MCP_PATH

client = MultiServerMCPClient(
        {
            "MariaDB_Server": {
                "transport": "stdio",
                "command": "uv",
                "args": [
                    "--directory",
                    MCP_PATH,
                    "run",
                    "server.py"
                ],
                "env": {
                    "DB_HOST": DB_HOST,
                    "DB_USER": DB_USER,
                    "DB_PASSWORD": DB_PASSWORD,
                    "DB_PORT": DB_PORT,
                    "DB_NAME": DB_NAME,
                    "MCP_READ_ONLY": "true",
                    "MCP_MAX_POOL_SIZE": "10"
                }
            }
        }
    )

async def get_tools():
    return await client.get_tools()
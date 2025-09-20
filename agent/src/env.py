import os
from dotenv import load_dotenv

load_dotenv()

def get_env_var(name: str, default_value=None) -> str:
    value = os.getenv(name)

    if value is None and default_value is None:
        raise RuntimeError(f"Missing required env var: {name}")

    if value is None:
        return default_value

    return value

DB_HOST = get_env_var("DB_HOST", "127.0.0.1")
DB_PORT = get_env_var("DB_PORT", "3306")
DB_NAME = get_env_var("DB_NAME", "spend_sense")
DB_USER = get_env_var("DB_USER")
DB_PASSWORD = get_env_var("DB_PASSWORD")
MCP_PATH = get_env_var("MCP_PATH")
from datetime import datetime

from langfuse import Langfuse
from langfuse.langchain import CallbackHandler

from src.env import LANGFUSE_PUBLIC_KEY, LANGFUSE_SECRET_KEY, LANGFUSE_URL, LANGFUSE_USER

langfuse = Langfuse(
    public_key=LANGFUSE_PUBLIC_KEY,
    secret_key=LANGFUSE_SECRET_KEY,
    host=LANGFUSE_URL
)

callback_handler = CallbackHandler()

def get_tracing_metadata():
    date = datetime.now().strftime("%Y%m%d")

    return {
    "langfuse_user_id": LANGFUSE_USER,
    "langfuse_session_id": date,
    "project": {
        "name": "Financial Agent",
        "id": "cmfsdpb2f0001pb07hikbh78g"
    },
    "org": {
        "name": "reputeo 2.0",
        "id": "cmfmg7qis0001pb070hd7ypfb"
    }
}
import dotenv
from langchain_ibm import ChatWatsonx
from pydantic import SecretStr

dotenv.load_dotenv()

parameters = {
    "temperature": 0,
}

URL = "https://eu-de.ml.cloud.ibm.com"
MODEL = "mistralai/mistral-large"
PROJECT_ID = "654e6d14-b819-47cd-8b05-66fa33d65de3"

chat = ChatWatsonx(
    model_id=MODEL,
    url=SecretStr(URL),
    project_id=PROJECT_ID,
    params=parameters,
)
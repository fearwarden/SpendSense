from src.database.database_connection import cursor

def get_chat_history():
    print("fetching data...")
    cursor.execute("SELECT * from chat_history limit 30")
    response = cursor.fetchall()
    print(response)
    return response

def write_to_chat_history(message: str, message_type: str):
    print("writing data for message: ", message)
    cursor.execute(
        "INSERT INTO chat_history (content,type) VALUES (?, ?)",
        (message, message_type))
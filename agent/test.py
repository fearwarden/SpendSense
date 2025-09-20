import csv
import requests
import json
import threading
import time

region_code = "eu-de"
iam_token = "eyJraWQiOiIyMDE5MDcyNCIsImFsZyI6IlJTMjU2In0.eyJpYW1faWQiOiJJQk1pZC02OTgwMDE0VkVaIiwiaWQiOiJJQk1pZC02OTgwMDE0VkVaIiwicmVhbG1pZCI6IklCTWlkIiwianRpIjoiNGI5MGFiNzItMGE2MS00ODJlLTg0ZjgtYmQ2ZDJiYTE3NGI4IiwiaWRlbnRpZmllciI6IjY5ODAwMTRWRVoiLCJnaXZlbl9uYW1lIjoiTWlsYW4iLCJmYW1pbHlfbmFtZSI6IkpvdmFub3ZpYyIsIm5hbWUiOiJNaWxhbiBKb3Zhbm92aWMiLCJlbWFpbCI6Im1pbGFuajE3MDVAZ21haWwuY29tIiwic3ViIjoibWlsYW5qMTcwNUBnbWFpbC5jb20iLCJhdXRobiI6eyJzdWIiOiJtaWxhbmoxNzA1QGdtYWlsLmNvbSIsImlhbV9pZCI6IklCTWlkLTY5ODAwMTRWRVoiLCJuYW1lIjoiTWlsYW4gSm92YW5vdmljIiwiZ2l2ZW5fbmFtZSI6Ik1pbGFuIiwiZmFtaWx5X25hbWUiOiJKb3Zhbm92aWMiLCJlbWFpbCI6Im1pbGFuajE3MDVAZ21haWwuY29tIn0sImFjY291bnQiOnsidmFsaWQiOnRydWUsImJzcyI6ImI3Y2FmNzg5NmJhMTQ3MzA4OTRhZTU3N2ZiMmQ4OGJlIiwiaW1zX3VzZXJfaWQiOiIxNDQ2MTc2NyIsImZyb3plbiI6dHJ1ZSwiaW1zIjoiMzEzMjQ3NSJ9LCJpYXQiOjE3NTgzMzYwMDMsImV4cCI6MTc1ODMzOTYwMywiaXNzIjoiaHR0cHM6Ly9pYW0uY2xvdWQuaWJtLmNvbS9pZGVudGl0eSIsImdyYW50X3R5cGUiOiJ1cm46aWJtOnBhcmFtczpvYXV0aDpncmFudC10eXBlOmFwaWtleSIsInNjb3BlIjoiaWJtIG9wZW5pZCIsImNsaWVudF9pZCI6ImRlZmF1bHQiLCJhY3IiOjEsImFtciI6WyJwd2QiXX0.QtXcX1FuNXjKGRuzZMpbJ7Ycd2_QopL-Zb59TwrpqKdPJu6ZYPonhruB35EQxQsRS0xwa-eIMF82BmgkjNWxqmnofYFeX4xyjFOno05ft5T24MaTDAP_HRtL3zxtEvAqHar6EetCNAC-pBJUaDUb6jqN1LC-ntp65xapWlJVxdCTRBJIZWut721ZLFz9J1m6-Hijjzn9jCJ3Iypk8gurhUiij5_w4YDeHHTQpLc9hiUFbkxqCnT1eWv6sMLz7kqggBmq-z14LWqCHtP8WI-IHJQ0mvK-Sx_ayIKOKf1k6NDbhK4qUc1JjTrgOnUFHmEdgIstGjTYULm9Ksjz3P6sUg"
instance_id = "b358fa3d-79d4-4b2d-9339-db79c1afdf33"
agent_id = "86010049-c0c9-496c-829d-b444b3d3e005"

shared_list = []
lock = threading.Lock()

def add_to_list(value):
    with lock:
        shared_list.append(value)

def send_request(method, url, endpoint, payload=None, headers=None):
    if headers is None:
        headers = {}

    res = requests.request(method, url + endpoint, json=payload, headers=headers, stream=True)
    events = []

    for line in res.iter_lines(decode_unicode=True):
        events.append(line)

    return events

def reconstruct_message(events):
    """
    Reconstructs full assistant messages from message.delta events.
    """
    message_parts = []

    for line in events:
        try:
            event = json.loads(line)
        except json.JSONDecodeError:
            continue

        # Only process assistant message delta events
        if event.get("event") == "message.delta":
            delta = event.get("data", {}).get("delta", {})
            role = delta.get("role")
            content = delta.get("content", [])

            if role == "assistant":
                for chunk in content:
                    # Each chunk has 'text'
                    text = chunk.get("text")
                    if text:
                        message_parts.append(text)

    # Combine all pieces into one string
    full_message = "".join(message_parts)
    return full_message

def call_agent(message, i, obj):
    print("Starting: ", i)
    payload = {
        "agent_id": agent_id,
        "message": {
            "role": "user",
            "content": message
        }
    }

    headers = {
        'Authorization': f"Bearer {iam_token}",
        'accept': "application/json"
    }

    message = ""

    try:
        events = send_request(
            "POST",
            "https://api.eu-de.watson-orchestrate.cloud.ibm.com",
            f"/instances/{instance_id}/v1/orchestrate/runs?stream=true&stream_timeout=180000&multiple_content=false",
            payload,
            headers=headers
        )
        message = reconstruct_message(events).replace("{{", "{").replace("}}", "}").strip()

    except Exception as e:
        print(e)
    try:
        message_json = json.loads(message)

        obj["category"] = message_json.get("category")
        obj["subcategory"] = message_json.get("subcategory")

        add_to_list(obj)
        print("Finished: ", i)
        return message_json
    except Exception as e:
        print(e, message)


def load_csv():
    with open("transactions.csv", "r") as f:
        reader = csv.reader(f)
        header = []
        data = []
        for i, line in enumerate(reader):
            if i == 0:
                header = line
                continue
            data.append(line)

    return header, data

def get_transaction_object(header, row):
    obj = {}
    for i in range(len(header)):
        obj[header[i]] = row[i]

    return obj

def main():
    header, data = load_csv()
    threads = []

    for i, row in enumerate(data):
        row_string = ",".join(row)
        obj = get_transaction_object(header, row)
        thread = threading.Thread(target=call_agent, args=(row_string, i, obj))
        thread.start()
        threads.append(thread)  # Keep track of threads
        # call_agent(row_string, i, obj)
        if i % 50 == 0 and i > 0:
            time.sleep(25)

    # Wait for all threads to finish
    for thread in threads:
        thread.join()

    with open("transactions.json", "w") as f:
        json.dump(shared_list, f)

# main()

def fix_json():
    l = []

    with(open("transactions.json", "r")) as f:
        l = json.load(f)

    for i in l:
        i["transactionTypeShort"] = i["transactionTypeShort"].upper()

        if i["category"] is None:
            i["category"] = "Misc"

        if i["subcategory"] == "":
            i["subcategory"] = None

        if i["subcategory"] is not None:
            if i["subcategory"] == "Restaurants / Takeaway":
                i["subcategory"] = "Restaurants_Takeaway"
            i["subcategory"] = i["subcategory"].upper()

        if i["category"] is not None:
            i["category"] = i["category"].upper()

    with open("transactions2.json", "w") as f:
        json.dump(l, f)

fix_json()
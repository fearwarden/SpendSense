import asyncio
import json
from src.agents.classification_agent import classify, Classification


async def classify_transaction(transaction, i):
    print(f"Processing transaction {i}")
    classification: Classification = await classify(json.dumps(transaction))
    transaction["category"] = classification.category
    transaction["subcategory"] = classification.subcategory
    print(f"Finished {i} - {classification.category}, {classification.subcategory}")
    return transaction


async def classify_all(batch_size=15, pause=3):
    with open("../transactions.json", "r") as f:
        transactions = json.load(f)

    categorized_transactions = []
    total = len(transactions)

    for start in range(0, total, batch_size):
        batch = transactions[start:start + batch_size]

        print(f"\n🚀 Starting batch {start // batch_size + 1} "
              f"({len(batch)} transactions)")

        tasks = [classify_transaction(t, i) for i, t in enumerate(batch, start=start)]

        try:
            results = await asyncio.gather(*tasks)

            categorized_transactions.extend(results)
        except Exception as e:
            print(e)

        # Pause between batches, unless it was the last one
        if start + batch_size < total:
            print(f"⏸ Pausing {pause}s before next batch...")
            await asyncio.sleep(pause)

    with open("../categorized_transactions.json", "w") as f:
        json.dump(categorized_transactions, f, indent=2)

    print("✅ All transactions processed.")


if __name__ == "__main__":
    asyncio.run(classify_all())

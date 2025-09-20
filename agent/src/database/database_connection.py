# Module Imports
import mariadb
import sys

from src.env import DB_HOST, DB_NAME, DB_PORT, DB_PASSWORD, DB_USER

# Connect to MariaDB Platform
try:
    conn = mariadb.connect(
        user=DB_USER,
        password=DB_PASSWORD,
        host=DB_HOST,
        port=int(DB_PORT),
        database=DB_NAME

    )
    conn.autocommit = True
except mariadb.Error as e:
    print(f"Error connecting to MariaDB Platform: {e}")
    sys.exit(1)

# Get Cursor
cursor = conn.cursor()
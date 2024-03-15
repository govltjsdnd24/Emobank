# uvicorn main:app --reload
from typing import Union

from fastapi import FastAPI
from pydantic import BaseModel

class Memo(BaseModel):
    memo: str

app = FastAPI()

@app.post("/")
def read_root():
    print()
    return {"Hello": "World"}

@app.post("/emotion/dic_test")
async def dic_test(memo: Memo):
    print(memo.memo)
    emotion_dic = {"fear" : "1", "surprise" : "2"}
    return emotion_dic

@app.post("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q} 


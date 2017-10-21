import http.client

conn = http.client.HTTPSConnection("apis.nbg.gr")

payload = "{\"sandbox_id\":\"741076029669376\"}"

headers = {
    'content-type': "text/json",
    'accept': "text/json"
    }

conn.request("POST", "/public/nbgapis/obp/v3.0.1/sandbox", payload, headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))

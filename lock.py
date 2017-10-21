from bitcoinrpc.authproxy import AuthServiceProxy, JSONRPCException
import time

# Client side

# firebase connection

asset_name = 'tshirt'

rpc_user='multichainrpc'
rpc_password='2JTdvMPbsJhqPfffZwpnCqhMfMH6nKTS6j9rdDns1NKM'
port = '7744'
server_address = '1STKxkPSfcKeYvaV3VJESTPxYaFDxAMkr1XPSc'
client_address = '1KG161HAdg9jXihRcq64cyZgJWfpZPEPJBbqTg'

rpc_connection = AuthServiceProxy("http://%s:%s@localhost:%s"%(rpc_user, rpc_password, port))

def get_asset_balance(asset_name):
	total_balances = rpc_connection.gettotalbalances()
	
	for asset in total_balances:
		if asset['name'] == asset_name:
			return int(asset['qty'])
	return -1
	
asset_balance = get_asset_balance(asset_name)	
	
while asset_balance >= 0:
	print 'Asset {} is locked'.format(asset_name)
	time.sleep(1)
	asset_balance = get_asset_balance(asset_name)
	
txid = -1

# todo add transactions	
	
print 'Asset {} was purchased with txid'.format(asset_name, txid)

	
	
	
	
	
	
	
	
	
	
	
		
	
	

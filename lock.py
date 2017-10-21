# Node-side script
from bitcoinrpc.authproxy import AuthServiceProxy, JSONRPCException
import time
from firebase import firebase

global firebase
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com', None)

asset_name = 'tshirt'

rpc_user='multichainrpc'
rpc_password='4vyx5afrN6JbvcAd41D9NGZDLYCsXjW9ENxVWji7QWfh'
port = '4352'
server_address = '1KwrjCUVPe2sBbyhVHWv95fGwRwxyFABDjxrGD'
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
		
print 'Asset {} was purchased with txid'.format(asset_name, txid)


	
	
	
	
	
	
	
	
	
	
		
	
	

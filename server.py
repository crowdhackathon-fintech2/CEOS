from bitcoinrpc.authproxy import AuthServiceProxy, JSONRPCException
import time

# TODO init firebase

# call firebase to fetch item
def get_current_purchased_asset():
	return ''
	
def post_last_transaction():
	print 'transaction posted'

def pop_last_txid():
	pass

#init RPC conn
rpc_user='multichainrpc'
rpc_password='2JTdvMPbsJhqPfffZwpnCqhMfMH6nKTS6j9rdDns1NKM'
port = '7744'
server_address = '1STKxkPSfcKeYvaV3VJESTPxYaFDxAMkr1XPSc'
client_address = '1KG161HAdg9jXihRcq64cyZgJWfpZPEPJBbqTg'

rpc_connection = AuthServiceProxy("http://%s:%s@localhost:%s"%(rpc_user, rpc_password, port))


products = {
	'tshirt' : 10.0,
	'shirt' : 20.0
}

transaction_counter = 1

try:
	for product in products.keys():
		rpc_connection.issue(server_address, product, products[product])
except:		
	print 'Products already initialized'
finally:
	assets = rpc_connection.listassets()
	print assets
	

prev_asset = get_current_purchased_asset()
prev_wallettransactions = rpc_connection.listwallettransactions()
 
while True:
	curr_asset = get_current_purchased_asset()
	
	if curr_asset != prev_asset:
		try:
			rpc_connection.sendasset(client_address, curr_asset, products[curr_asset])
		except:
			continue
		current_wallettransactions = rpc_connection.listwallettransactions()
		
		prev_trans = prev_wallettransactions[-1]
		current_trans =  current_wallettransactions[-1]
		
		# check validity:
		if prev_trans != current_trans and current_trans['valid']:
			print 'Transaction validated'
			print current_trans
			print 'Posting to firebase'
			
			post_last_transaction()
		
		prev_wallettransactions = current_wallettransactions
	
	
	
	
		transaction_counter += 1
	
	time.sleep(5)	

	
	
def checker():
	# door check
	pass		
		
		

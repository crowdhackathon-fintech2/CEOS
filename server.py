#server-side script
from bitcoinrpc.authproxy import AuthServiceProxy, JSONRPCException
import time, threading
from firebase import firebase

global firebase
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com', None)

def get_current_purchased_asset():
	return str(firebase.get('/current_asset', None))
	
def post_last_transaction(): 
	print 'transaction posted'

def pop_last_txid():
	pass

print get_current_purchased_asset()

#init RPC conn
rpc_user='multichainrpc'
rpc_password='EreNfQzTtSBVr3M3R4Mnmk7LogtK9CLLkDtBesBjF3eS'
port = '4352'

rpc_connection = AuthServiceProxy("http://%s:%s@localhost:%s"%(rpc_user, rpc_password, port))

server_address = rpc_connection.getaddresses()[0] 
client_address = '1KG161HAdg9jXihRcq64cyZgJWfpZPEPJBbqTg'

global products
products = {
	'test1' : 1000.0,
	'test2' : 1000.0,
	'tshirt' : 99.0,
	'shirt' : 10.0
}

global transaction_counter
transaction_counter = 1

try:
	for product in products.keys():
		rpc_connection.issue(server_address, product, products[product])
except:		
	print 'Products already initialized'
finally:
	global assets
	assets = rpc_connection.listassets()
	print assets
	
def transaction_validator(rpc_connection): 
	prev_asset = get_current_purchased_asset()
	prev_wallettransactions = rpc_connection.listwallettransactions()
	

	while True:
		curr_asset = get_current_purchased_asset()
		
		print curr_asset
		if curr_asset != prev_asset:
			try:
				print '{} {}'.format(curr_asset, products[curr_asset])
				rpc_connection.sendasset(client_address, curr_asset, products[curr_asset])
			except:
				print 'Error'
				break
			finally:
				print rpc_connection.gettotalbalances()
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
		
		time.sleep(1)	

	
	
def checker(rpc_connection):
	
	while True:
		curr_txid = pop_last_txid()
		if curr_txid != '':
			curr_transactions = map(lambda x : x['txid'], rpc_connection.listwallettransactions())
			if curr_txid not in curr_transactions:
				print 'Something was stolen here'
			else:
				continue
		time.sleep(1)	
		
transaction_validator(rpc_connection)
		
	
		
		

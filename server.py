#server-side script
from config import *


def get_current_purchased_asset():
	return str(firebase.get('/current_asset', None))
	
def post_last_transaction(): 
	print 'transaction posted'

def pop_last_txid():
	pass
	
def set_proceed(val):
	pass
	
def set_checkout(val):
	pass	
		
def get_checkout():
	pass

def get_assets():
	pass

global products
products = {
	'tshirt1' : 20.40,
	'tshirt2' : 25.20
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
		while get_checkout():
			assets = get_assets()
			for curr_asset in assets:
				try:
					rpc_connection.sendasset(client_address, curr_asset, products[curr_asset])
				except:
					except:
					print 'Error'
				finally:
					print rpc_connection.gettotalbalances()
				
				# validate
				number_of_transactions = len(assets)
				curr_wallettransactions = rpc_connection.listwallettransactions()
				i = -1
				tx = curr_wallettransactions[-i]
				while abs(i) <= n:
					tx = curr_transactions[-i]
					if tx['valid']:	
						print 'Transaction validated'
						print current_trans
						print 'Posting to firebase'
						post_last_transaction()
						transaction_counter += 1
					else:
						print 'something went wrong'
						break
					i -= 1 
				set_proceed(True)
				set_checkout(False)
				delete_assets()
				
				time.sleep(5)
				set_proceed(False)
				
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
		
	
		
		

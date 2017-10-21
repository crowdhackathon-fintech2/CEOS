#server-side script
from config import *

def get_current_purchased_asset():
	return str(firebase.get('/current_asset', None))
	
def post_last_transaction(): 
	print 'transaction posted'

def pop_last_txid():
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
		blockchain.issue(server_address, product, products[product])
except:		
	print 'Products already initialized'
finally:
	global assets
	assets = blockchain.listassets()
	print assets
	
def transaction_validator(blockchain): 
	prev_asset = get_current_purchased_asset()
	prev_wallettransactions = blockchain.listwallettransactions()
	
	while True:
		while checkoutGetter():
			assets = itemsGetter()
			for curr_asset in assets:
				try:
					blockchain.sendasset(client_address, curr_asset, products[curr_asset])
				except:
					print 'Error'
				finally:
					print blockchain.gettotalbalances()
				
				# validate
				# check last n transactions
				number_of_transactions = len(assets)
				curr_wallettransactions = blockchain.listwallettransactions()
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
				resetDb()
				
				
		time.sleep(1)
	
	
def checker(blockchain):
	
	while True:
		curr_txid = pop_last_txid()
		if curr_txid != '':
			curr_transactions = map(lambda x : x['txid'], blockchain.listwallettransactions())
			if curr_txid not in curr_transactions:
				print 'Something was stolen here'
			else:
				continue
		time.sleep(1)	
		
transaction_validator(blockchain)
		
	
		
		

#server-side script
from config import *

	
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
		blockchain.issue(server_address, product, products[product], 0.01)
except:		
	print 'Products already initialized'
finally:
	global assets
	assets = blockchain.listassets()
	print assets

	
def transaction_validator(blockchain): 
	print 'Initial status'
	prev_wallettransactions = blockchain.listwallettransactions()
	for tx in prev_wallettransactions:
		print tx['txid']
	
	for w in wt:
		print w['txid']
		firebase.put('/','transactions',{w['txid']:1})	

	while True:
		while checkoutGetter():
			assets = itemsGetter()
			for curr_asset in assets:
				try:
					blockchain.sendasset(client_address, curr_asset, products[curr_asset])
				except:
					print 'Error'
					checkoutSetter(0)
				finally:
					print 'Balances'

					balances =  blockchain.gettotalbalances()
					for b in balances:
						print b

					print 'Wallet transactions'					
					wt =  blockchain.listwallettransactions()
					for w in wt:
						print w['txid']
						firebase.put('/','transactions',{w['txid']:1})


							
				# validate
				# check last n transactions
				#number_of_transactions = len(assets)
				#curr_wallettransactions = blockchain.listwallettransactions()
				#i = -1
				#tx = curr_wallettransactions[-i]
				#while abs(i) <= n:
				#	tx = curr_transactions[-i]
				#	if tx['valid']:	
				#		print 'Transaction validated'
				#		print current_trans
				#		print 'Posting to firebase'
				#		transaction_counter += 1
				#	else:
				#		print 'something went wrong'
				#		break
				#	i -= 1 
				#resetDb()
				
				
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
		
	
		
		

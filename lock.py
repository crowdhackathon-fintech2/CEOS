# Node-side script
from config import *

asset_name = 'tshirt'

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


	
	
	
	
	
	
	
	
	
	
		
	
	

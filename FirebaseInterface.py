from firebase import firebase
global firebase 
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com/', None)
def checkoutGetter():
	return firebase.get('/checkout',None)['value']
def checkoutSetter(val):
	firebase.put('/','checkout',{'value':val})
	return
def itemsGetter():
	temp=firebase.get('/items',None)
	return temp.keys()
def proceedSetter(val):
	firebase.put('/','proceed',{'value':val})
	return
def resetDb():
	items=itemsGetter()
	for i in items:
		firebase.delete('/items',i)
	proceedSetter(1)
	checkoutSetter(0)









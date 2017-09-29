import urllib2
import sys

TARGET = 'http://crypto-class.appspot.com/po?er='
#--------------------------------------------------------------
# padding oracle
#--------------------------------------------------------------
class PaddingOracle(object):
    def query(self, q):
        target = TARGET + urllib2.quote(q)    # Create query URL
        req = urllib2.Request(target)         # Send HTTP request to server
        try:
            f = urllib2.urlopen(req)          # Wait for response
        except urllib2.HTTPError, e:          
            print "We got: %d" % e.code       # Print response code
            if e.code == 404:
                return True # good padding
            return False # bad padding


##    guess_str = hex(guess)
##    guess_str = guess_str[2:5]
##    guess_str = guess_str.zfill(2)

if __name__ == "__main__":
    po = PaddingOracle()
##    test = po.query(sys.argv[])       # Issue HTTP query with the given argument


secretmsg = 'f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0'
secretmsg = 'f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd'
secretmsg = 'f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4'
revisemsg = secretmsg;

msg_guess = [9,9,9,9,9,9,9,9,9]

for byte in range(10,17):
    
    index = 96 - 2 * byte;
    print 'index is',index

    revisemsg = secretmsg
    for pp in range(1,byte):
        cipher_byte = int(secretmsg[(index + pp * 2):(index + 2 + pp * 2)],16)
        
        pad = byte^msg_guess[pp - 1]^cipher_byte
        pad = hex(pad)
        pad = pad[2:5]
        pad = pad.zfill(2)

        print pad
        revisemsg = revisemsg[0:(index + pp * 2)] + pad + revisemsg[(index + 2 + pp * 2):128]
        
    for guess in range(0,256):

        cipher_byte = int(secretmsg[(index):(index + 2)],16)
        
        pad = byte ^ guess ^ cipher_byte


##        pad = 
        pad = hex(pad)
        pad = pad[2:5]
        pad = pad.zfill(2)

        print 'The guess is ',guess

        revisemsg = revisemsg[0:(index)] + pad + revisemsg[(index + 2):128]
        
##        print 'revise message is: ',revisemsg,'\n'
        req = revisemsg
        test = po.query(req)

        if test == True:

            msg_guess.insert(0,guess)

            print '\nCorrect\n'    
            print 'Now the msg already got is ',msg_guess
            print '\n'
            break
        

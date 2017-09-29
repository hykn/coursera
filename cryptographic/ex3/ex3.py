##import sys
##reload(sys)
##sys.setdefaultencoding('utf-8')


import hashlib

block_care = [0,0,0,32,102,116,121,112,105,115,111,109,0,128]#,2,0,105,115,111,109,105,115,111,50,97,118,99,49,109,112,52,49,0,6,240,12,109,111,111,118,0,0,0,108,109,118,104,100,0,0,0,0,124,37,176,128,124,37,176,128,0,0,3,232,0,13,100,100,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,64,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,3,50,6,116,114,97,107,0,0,0,92,116,107,104,100,0,0,0,15,124,37,176,128,124,37,176,128,0,0,0,1,0,0,0,0,0,13,100,99,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,64,0,0,0,3,192,0,0,2,28,0,0,0,0,0,36,101,100,116,115,0,0,0,28,101,108,115,116,0,0,0,0,0,0,0,1,0,13,100,99,0,0,0,2,0,1,0,0,0,3,49,126,109,100,105,97,0,0,0,32,109,100,104,100,0,0,0,0,124,37,176,128,124,37,176,128,0,0,0,15,0,0,51,109,85,196,0,0,0,0,0,45,104,100,108,114,0,0,0,0,0,0,0,0,118,105,100,101,0,0,0,0,0,0,0,0,0,0,0,0,86,105,100,101,111,72,97,110,100,108,101,114,0,0,3,49,41,109,105,110,102,0,0,0,20,118,109,104,100,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,36,100,105,110,102,0,0,0,28,100,114,101,102,0,0,0,0,0,0,0,1,0,0,0,12,117,114,108,32,0,0,0,1,0,3,48,233,115,116,98,108,0,0,0,153,115,116,115,100,0,0,0,0,0,0,0,1,0,0,0,137,97,118,99,49,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,192,2,28,0,72,0,0,0,72,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24,255,255,0,0,0,51,97,118,99,67,1,100,0,31,255,225,0,26,103,100,0,31,172,217,64,240,17,126,240,17,0,0,3,0,1,0,0,3,0,30,15,24,49,150,1,0,6,104,235,226,75,34,192,0,0,0,24,115,116,116,115,0,0,0,0,0,0,0,1,0,0,51,109,0,0,0,1,0,0,0,236,115,116,115,115,0,0,0,0,0,0,0,55,0,0,0,1,0,0,0,251,0,0,1,174,0,0,2,168,0,0,3,162,0,0,4,156,0,0,5,150,0,0,6,144,0,0,7,138,0,0,8,73,0,0,9,67,0,0,10,61,0,0,11,55,0,0,12,49,0,0,13,43,0,0,14,37,0,0,15,31,0,0,16,25,0,0,17,19,0,0,18,13,0,0,19,7,0,0,20,1,0,0,20,251,0,0,21,245,0,0,22,239,0,0,23,233,0,0,24,227,0,0,25,221,0,0,26,215,0,0,27,209,0,0,28,203,0,0,29,197,0,0,30,191,0,0,31,185,0,0,32,179,0,0,33,173,0,0,34,167,0,0,35,161,0,0,36,155,0,0,37,101,0,0,38,95,0,0,39,89,0,0,40,83,0,0,40,223,0,0,41,217,0,0,42,211,0,0,43,205,0,0,44,199,0,0,45,193,0,0,46,62,0,0,47,56,0,0,48,50,0,0,49,44,0,0,50,38,0,0,51,32,0,1,147,152,99,116,116,115,0,0,0,0,0,0,50,113,0,0,0,3,0,0,0,2,0,0,0,1,0,0,0,3,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,3,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,3,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,2,0,0,0,1,0,0,0,5,0,0,0,1,0,0,0,2,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,5,0,0,0,1,0,0,0,2,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,2,0,0,0,1,0,0,0,5,0,0,0,1,0,0,0,2,0,0]
##block_care =  [82,111,115,101,116,116,97,32,99,111,100,101]

block_care_string = "".join(map(chr,block_care))

print block_care_string


##h_0_test = hashlib.sha256(block_care_string.encode()).hexdigest();

##print h_0_test

a = "zhongwen"

print a
##import sys
##import hashlib
##
##
### BUF_SIZE is totally arbitrary, change for your app!
##BUF_SIZE = 65536  # lets read stuff in 64kb chunks!
##
##md5 = hashlib.md5()
##sha1 = hashlib.sha1()
##
##
##with open(sys.argv[1], 'rb') as f:
##    while True:
##        data = f.read(BUF_SIZE)
##        if not data:
##            break
##        md5.update(data)
##        sha1.update(data)
##
##print("MD5: {0}".format(md5.hexdigest()))
##print("SHA1: {0}".format(sha1.hexdigest()))

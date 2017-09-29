
def BinarySearch(array,t):
    low = 0
    height = len(array)-1
    
    while low < height:
        mid = (low + height)/2
        if array[mid] < t:
            low = mid + 1
        elif array[mid] > t:
            height = mid - 1
        else:
            return mid
    raise

import gmpy2
from gmpy2 import mpz

p = mpz(13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171);

g = mpz(11717829880366207009516117596335367088558084999998952205599979459063929499736583746670572176471460312928594829675428279466566527115212748467589894601965568);

h = mpz(3239475104050450443565264378728065788649097520952449527834792452971981976143292558073856937958553180532878928001494706097394108577585732452307673444020333);


(gg,ss,tt) = gmpy2.gcdext(p,g)


g_inv = p + tt;


B = 2**20


left_value = h
left_table = [h]

for x_1 in range(1,B + 1):
    left_value = gmpy2.t_mod(left_value * g_inv,p)
    left_table.append(left_value)


left_table.sort()

##left_table_hash = map(hash,left_table)

right_value = 1
right_power = gmpy2.t_mod(g**B,p)

for x_0 in range(0,B + 1):
    try:
        
##        x_1_correct = left_table.index(right_value)
        x_1_correct = BinarySearch(left_table,right_value)
        x_0_correct = x_0
        print 'Search Done'
        break
    except:
##        print x_0
        right_value = gmpy2.t_mod(right_value * right_power,p)



left_value = h
left_table_ori = [h]

for x_1 in range(1,B + 1):
    left_value = gmpy2.t_mod(left_value * g_inv,p)
    left_table_ori.append(left_value)


try:
    x_1_correct = left_table_ori.index(left_table[x_1_correct])
except:
    print 'Error, please check your program'
    
    

x = x_0_correct * B + x_1_correct
print 'x_1 = ',x_1_correct
print 'x_0 = ',x_0_correct
print x

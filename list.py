list = [1,2,3,4,5,"mamupandu", 3.5, True]
print(type(list))
print(list[2])
print(len(list))
list.append(10)
print(list)
list[7] = True
list.__delitem__(6)
print(list)
a = [1,2,3,4,5]
b = ['a', 'b', 'c', 'd', 'e']
c = a + b
print(c)

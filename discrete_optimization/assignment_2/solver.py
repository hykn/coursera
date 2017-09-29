#!/usr/bin/python
# -*- coding: utf-8 -*-

from collections import namedtuple
import copy
import sys
import math

Item = namedtuple("Item", ['index', 'value', 'weight'])
DP_entry = namedtuple("DP_entry", ['value', 'istaken'])

def solve_it(input_data):
    # Modify this code to run your optimization algorithm

    # parse the input
    lines = input_data.split('\n')

    firstLine = lines[0].split()
    item_count = int(firstLine[0])
    capacity = int(firstLine[1])

    items = []

    for i in range(1, item_count+1):
        line = lines[i]
        parts = line.split()
        items.append(Item(i-1, int(parts[0]), int(parts[1])))

    # a trivial greedy algorithm for filling the knapsack
    # it takes items in-order until the knapsack is 
    # value, taken = greedy_solver(items,capacity)

    # value, taken = dp_solver(items,capacity)
    # value, taken = greedy_solver(items, capacity)

    # sys.setrecursionlimit(2000)
    # obj_dfs_solver = cls_dfs_solver(items, capacity)
    # value, taken = obj_dfs_solver.dfs_solver()
    hs_algorithm = horowitz_sahni_algorithm(items,capacity)

    value, taken = hs_algorithm.hs_solver()
    output_data = str(value) + ' ' + str(0) + '\n'
    output_data += ' '.join(map(str, taken))
    return output_data

def greedy_solver(items, capacity):
    
    item_count = len(items)

    value = 0
    weight = 0
    taken = [0]*len(items)

    items_sorted = copy.copy(items)
    items_sorted.sort(key = lambda Item:(Item.value)/(Item.weight), reverse = True)

    for item in items_sorted:
        if weight + item.weight <= capacity:
            taken[item.index] = 1
            value += item.value
            weight += item.weight

    return value, taken

def dp_solver(items, capacity):
    dp_table = []
    item_count = len(items)
    dp_first_line = []

    for item in items:
        dp_first_line.append(DP_entry(0, 0))
    dp_table.append(dp_first_line)

    for cc in range(1, capacity + 1):
        dp_line = []
        for item in items:
            index = item.index;
            value = item.value;
            weight = item.weight
            dp_entry = DP_entry(0, 0)
            if weight <= cc :
                if index == 0:
                     dp_entry = DP_entry(value, 1)
                else:
                    if dp_line[index - 1].value >= value + dp_table[cc - weight][index - 1].value:
                        dp_entry = DP_entry(dp_line[index - 1].value, 0)
                    else:
                        dp_entry = DP_entry(value + dp_table[cc - weight][index - 1].value, 1)
            else:
                if index > 0:
                    dp_entry = DP_entry(dp_line[index - 1].value, 0)
            dp_line.append(dp_entry)
        dp_table.append(dp_line)

    cc = capacity
    ii = item_count - 1
    taken = [0]*len(items)

    # for cc in range(0, capacity + 1):
    #     for item in items:
    #         print(dp_table[cc][item.index].value, ',', dp_table[cc][item.index].istaken, ' ', end = '')
    #     print('')

    while cc > 0 and ii >= 0:
        while ii >= 0:
            if dp_table[cc][ii].istaken == 1:
                taken[ii] = 1
                cc = cc - items[ii].weight
                ii = ii - 1
                break
            else:
                ii = ii - 1

    print("finished")
    return dp_table[capacity][item_count - 1].value, taken


class horowitz_sahni_algorithm:
    def __init__(self, items, capacity):

        self.items = copy.copy(items)
        self.items.sort(key = lambda Item:(Item.value)/(Item.weight), reverse = True)

        self.capacity = capacity
        self.items_num = len(items)

    def hs_solver(self):
        total_value_best = 0
        total_value_current = 0
        rest_capacity = self.capacity
        self.items.append(Item(self.items_num, 0, math.inf))

        taken_current = [0] * self.items_num
        taken_best = [0] * self.items_num

        # print("value", list(self.items[ii].value for ii in range(0,self.items_num)))
        
        j = 0
        step = 2
        while (True):
            # print("z = ", total_value_current)
            # print("c = ", rest_capacity)
            # print("x = ", taken_current)
            # step 2: compute upper bound U1
            if step == 2:
                r_total = 0
                r = 0
                for r in range(j, self.items_num):
                    r_total += self.items[r].weight
                    if r_total > rest_capacity:
                        break

                p_r = self.items[r].value
                weight_r = self.items[r].weight

                u = sum(self.items[ii].value for ii in range(j, r)) \
                + math.floor((rest_capacity - sum(self.items[ii].weight for ii in range(j, r))) * p_r/weight_r)
                
                if total_value_best >= total_value_current + u:
                    step = 5
                else:
                    step = 3

            # step 3
            elif step == 3:
                while self.items[j].weight <= rest_capacity:
                    rest_capacity -= self.items[j].weight
                    total_value_current += self.items[j].value
                    taken_current[j] = 1
                    j += 1

                if j <= self.items_num - 1:
                    taken_current[j] = 0
                    j += 1
                elif j < self.items_num - 1:
                    step = 2
                elif j == self.items_num - 1:
                    step = 3
                else:
                    step = 4

            elif step == 4:
                if total_value_current > total_value_best:
                    total_value_best = total_value_current
                    taken_best = list(taken_current)
                
                j = self.items_num - 1
                if taken_current[self.items_num - 1] == 1:
                    rest_capacity += self.items[self.items_num - 1].weight
                    total_value_current -= self.items[self.items_num - 1].value
                    taken_current[self.items_num - 1] = 0
                step = 5

            # step 5
            elif step == 5:
                i = -1
                for ii in range(j - 1, -1 , -1):
                    if taken_current[ii] == 1:
                        i = ii
                        break
                if i == -1:
                    value = total_value_best
                    taken = [0] * self.items_num

                    for ii in range(0, self.items_num):
                        taken[self.items[ii].index] = taken_best[ii]
                    return value, taken

                rest_capacity += self.items[i].weight
                total_value_current -= self.items[i].value
                taken_current[i] = 0
                j = i + 1
                step = 2

class cls_dfs_solver:
    def __init__(self, items, capacity):
        self.search_node_num = 0

        items_sorted = copy.copy(items)
        items_sorted.sort(key = lambda Item:(Item.value)/(Item.weight), reverse = True)
        self.items = list(items_sorted)

        self.best_value = 0
        self.best_taken = [0] * len(items)
        self.remain_capacity = capacity
        self.iter_num = 0

        self.taken = [0] * len(items)
        self.total_value = 0

    def dfs_solver(self):
        self.dfs_solver_aux()
        return self.best_value, self.best_taken

    def dfs_solver_aux(self):
        # print(self.iter_num, self.remain_capacity, self.total_value, self.taken, self.best_value, self.best_taken)
        # self.search_node_num += 1
        # if self.search_node_num > 1000:
        #     return

        if self.total_value > self.best_value:
            self.best_taken = list(self.taken)
            self.best_value = self.total_value

        if self.iter_num > len(self.items) - 1:
            return
        
        if self.remain_capacity < self.items[self.iter_num].weight: 
            self.taken[self.iter_num] = 0
            self.iter_num = self.iter_num + 1
            self.dfs_solver_aux()
            self.iter_num = self.iter_num - 1
        else:
            self.remain_capacity = self.remain_capacity - self.items[self.iter_num].weight
            self.total_value = self.total_value + self.items[self.iter_num].value
            self.taken[self.iter_num] = 1
            self.iter_num = self.iter_num + 1
            self.dfs_solver_aux()
            self.iter_num = self.iter_num - 1

            self.remain_capacity = self.remain_capacity + self.items[self.iter_num].weight
            self.total_value = self.total_value - self.items[self.iter_num].value
            self.taken[self.iter_num] = 0
            self.iter_num = self.iter_num + 1
            self.dfs_solver_aux()               
            self.iter_num = self.iter_num - 1    

if __name__ == '__main__':
    # if len(sys.argv) == 1:
    #     file_location = "./data/ks_4_0"
    #     with open(file_location, 'r') as input_data_file:
    #         input_data = input_data_file.read()
    #     print(solve_it(input_data))
        
    if len(sys.argv) > 1:
        file_location = sys.argv[1].strip()
        with open(file_location, 'r') as input_data_file:
            input_data = input_data_file.read()
        print(solve_it(input_data))
    else:
        print('This test requires an input file.  Please select one from the data directory. (i.e. python solver.py ./data/ks_4_0)')


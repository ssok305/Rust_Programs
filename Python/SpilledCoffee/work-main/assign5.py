"""
Juliet Smith
For this assignment there is no automated testing. You will instead submit
your *.py file in Canvas. I will download and test your program from Canvas.
"""
# import copy will help insure that I don't lose the information of my breaders
# when I'm making the new generation
import copy
from stringprep import in_table_a1
import time
import sys
import random
INF = sys.maxsize


def adjMatFromFile(filename):
    """ Create an adj/weight matrix from a file with verts, neighbors, and weights. """
    f = open(filename, "r")
    n_verts = int(f.readline())
    print(f" n_verts = {n_verts}")
    adjmat = [[None] * n_verts for i in range(n_verts)]
    for i in range(n_verts):
        adjmat[i][i] = 0
    for line in f:
        int_list = [int(i) for i in line.split()]
        vert = int_list.pop(0)
        assert len(int_list) % 2 == 0
        n_neighbors = len(int_list) // 2
        neighbors = [int_list[n] for n in range(0, len(int_list), 2)]
        distances = [int_list[d] for d in range(1, len(int_list), 2)]
        for i in range(n_neighbors):
            adjmat[vert][neighbors[i]] = distances[i]
    f.close()
    return adjmat


def TSPwGenAlgo(g,
        max_num_generations= 100000,
        population_size = 30,
        mutation_rate = 0.9,
        explore_rate = 0.5
    ):
    """ A genetic algorithm to attempt to find an optimal solution to TSP  """

    # NOTE: YOU SHOULD CHANGE THE DEFAULT PARAMETER VALUES ABOVE TO VALUES YOU
    # THINK WILL YIELD THE BEST SOLUTION FOR A GRAPH OF ~100 VERTS AND THAT CAN
    # RUN IN 5 MINUTES OR LESS (ON AN AVERAGE LAPTOP COMPUTER)
    total_mutations = 0
    mutation_rate = mutation_rate * 100
    explore_rate = max_num_generations * explore_rate
    start_time = time.time()
    solution_path = [] # list of n+1 verts representing sequence of vertices with lowest total distance found
    solution_distance = INF # distance of final solution path, note this should include edge back to starting vert
    avg_path_each_generation = [INF] * max_num_generations # store average path length path across individuals in each generation
    """This is where the individuals are initualized"""
    # create individual members of the population
    verts = list(range(len(g)))  # this is list of vectors in g
    population = [0] * population_size # this will hold lists of randomized vectors
    # initialize individuals to an initial 'solution'
    # This will initialize the population based on the vector list.
    # 0 and last spot should be the same vector
    for i in range(population_size):
        population[i] = (random.sample(verts[1:], len(verts) - 1))
    # loop for x number of generations (can also choose to add other early-stopping criteria)
    min_dist = INF
    i = 0
    elapsed_time = start_time - time.time()
    five_minutes = 5 * 60
    while (i < max_num_generations) and (elapsed_time < five_minutes):
        # calculate fitness of each individual in the population
        fitness = [INF]*population_size  # this stores the distane for each individuale in tuples (distance, index in p)
        for j in range(population_size):
            # my assumption is that the start and finish is always the first vertex
            # this grabs the distances from the first to secound and last to first
            distance = g[0][population[j][0]] + g[0][population[j][-1]]
            for k in range(len(g)-1):
                distance += g[0][population[j][k]]
            if(distance < min_dist):
                # this isnures that the best solution though all generations is kept
                min_dist = distance
                solution_path = copy.deepcopy(population[j])
                # this was used to test efficiency of my algorithm
                print("current solution distance:", min_dist,"Generation it was found in:", i, time.time() - start_time)
            fitness[j] = (distance, j)
        elapsed_time = time.time() - start_time
        solution_distance = min_dist
        # print("current best: ", fitness[0])
        # calculate average path length across individuals in this generation
        avg_path_length = 0
        for h in range(population_size):
            dist = fitness[h]
            avg_path_length += dist[0]
        avg_path_length = avg_path_length/population_size
        # and store in avg_path_each_generation
        avg_path_each_generation[i] = avg_path_length
        # select the individuals to be used to spawn the generation, then create
        fitness.sort()  # this sorts the population based on their distances/fitness
        breeders = []  # will store half the population to reproduce
        if(i < explore_rate):
            rando = random.randint(len(breeders), len(fitness) - 1)
            rando = fitness[rando]
            rando = copy.deepcopy(population[rando[1]])
            breeders = fitness[0:(population_size // 2) - 1]
            for j in range(len(breeders)):
                temp = breeders[j]
                breeders[j] = copy.deepcopy(population[temp[1]])
            breeders.append(rando)
        else:
            breeders = fitness[0:population_size // 2]
            for j in range(len(breeders)):
                temp = breeders[j]
                breeders[j] = population[temp[1]]
        # individuals of the new generation (using some form of crossover)
        # since i'm making two children from each pair, only running though at half time
        for p in range(population_size // 2):
            index = p * 2  # child1 placed @ population[index], child2 = population[index + 1]
            mom = breeders[p]  # insures that every breader will be used at least once
            dad = random.randint(0, len(breeders) - 1)
            child1 = [INF] * len(mom)
            used1 = [False] * (len(mom) + 1)
            used1[0] = True
            child2 = [INF] * len(mom)
            used2 = [False] * (len(mom) + 1)
            used2[0] = True
            # this makes sure that the mom is bred with itself
            while(dad == p):
                dad = random.randint(0, len(breeders) - 1)
            dad = breeders[dad]
            xarea = len(mom) // 3  # how large the cross over will be
            visted1 = 0
            visted2 = 0
            # this gets the cross over genes, always from the beggining
            for q in range(xarea):
                child1[q] = mom[q]
                used1[child1[q]] = True
                child2[q] = dad[q]
                used2[child2[q]] = True
                visted1 += 1
                visted2 += 1
            x1 = 0
            # this finishes child1 with the dad's genes in order as much as possible
            while(visted1 < len(mom)):
                if(x1 == len(mom)):
                    x1 = 0
                temp = dad[x1]
                if(used1[temp] == False):
                    child1[visted1] = dad[x1]
                    visted1 += 1
                x1 += 1
            # this finishes child2 with the mom's genes in order as much as possible
            x2 = 0
            while(visted2 < len(mom)):
                if(x2 == len(mom)):
                    x2 = 0
                if(used2[mom[x2]] == False):
                    child2[visted2] = mom[x2]
                    visted2 += 1
                x2 += 1
            mutate = random.randint(0, 100)
            if(mutate <= mutation_rate):
                rando = random.randint(0, len(mom) -1)
                child1[0], child1[rando] = child1[rando], child1[0]
                child2[0], child2[rando] = child2[rando], child2[0]
                total_mutations += 1
                # print("mutation happedned!")
            population[p] = child1
            population[p + 1] = child2
        i += 1
        elapsed_time = time.time() - start_time
        # allow for mutations (shuold be based on mutation_rate, should not happen too often)
        # ...
    # calculate and *verify* final solution
    solution_path.insert(0,0)
    solution_path.append(0)
    # print("total mutations: ", total_mutations)
   
    return {
            'solution_path': solution_path,
            'solution_distance': solution_distance,
            'evolution': avg_path_each_generation
           }


def TSPwDynProg(g):
    """ (10pts extra credit) A dynamic programming approach to solve TSP """
    solution_path = [] # list of n+1 verts representing sequence of vertices with lowest total distance found
    solution_distance = INF # distance of solution path, note this should include edge back to starting vert
    #...

    return {
            'solution_path': solution_path,
            'solution_distance': solution_distance,
           }


def TSPwBandB(g):
    """ (10pts extra credit) A branch and bound approach to solve TSP """
    solution_path = [] # list of n+1 verts representing sequence of vertices with lowest total distance found
    solution_distance = INF # distance of solution path, note this should include edge back to starting vert

    #...

    return {
            'solution_path': solution_path,
            'solution_distance': solution_distance,
           }


def assign05_main():
    """ Load the graph (change the filename when you're ready to test larger ones) """
    g = adjMatFromFile("complete_graph_n100.txt")

    # Run genetic algorithm to find best solution possible
    start_time = time.time()
    res_ga = TSPwGenAlgo(g)
    elapsed_time_ga = time.time() - start_time
    print(f"GenAlgo runtime: {elapsed_time_ga:.2f}")
    print(f"  sol dist: {res_ga['solution_distance']}")
    print(f"  sol path: {res_ga['solution_path']}")

    # (Try to) run Dynamic Programming algorithm only when n_verts <= 10
    if len(g) <= 10:
        start_time = time.time()
        res_dyn_prog = TSPwDynProg(g)
        elapsed_time = time.time() - start_time
        if len(res_dyn_prog['solution_path']) == len(g) + 1:
            print(f"Dyn Prog runtime: {elapsed_time:.2f}")
            print(f"  sol dist: {res_dyn_prog['solution_distance']}")
            print(f"  sol path: {res_dyn_prog['solution_path']}")

    # (Try to) run Branch and Bound only when n_verts <= 10
    if len(g) <= 10:
        start_time = time.time()
        res_bnb = TSPwBandB(g)
        elapsed_time = time.time() - start_time
        if len(res_bnb['solution_path']) == len(g) + 1:
            print(f"Branch & Bound runtime: {elapsed_time:.2f}")
            print(f"  sol dist: {res_bnb['solution_distance']}")
            print(f"  sol path: {res_bnb['solution_path']}")


# Check if the program is being run directly (i.e. not being imported)
if __name__ == '__main__':
    assign05_main()

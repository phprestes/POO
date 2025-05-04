import random
from collections import Counter

class Dado:
    def __init__(self, n=6, seed=None):
        self.lados = n
        self.ultimo = 0
        self.rand = random.Random(seed)
        self.rolar()
    
    def rolar(self):
        self.ultimo = self.rand.randint(1, self.lados)
        return self.ultimo
    
    def getLado(self):
        return self.ultimo
    
    def __str__(self):
        if self.lados != 6:
            return "Não há representação para esse dados"
        
        s = "+-----+\n"
        s000 = "|     |\n"
        s001 = "|    *|\n"
        s010 = "|  *  |\n"
        s100 = "|*    |\n"
        s101 = "|*   *|\n"
        s111 = "|* * *|\n"

        match self.getLado():
            case 1:
                s += (s000 + s010 + s000)
            case 2:
                s += (s100 + s000 + s001)
            case 3:
                s += (s100 + s010 + s001)
            case 4:
                s += (s101 + s000 + s101)
            case 5:
                s += (s101 + s010 + s101)
            case 6:
                s += (s111 + s000 + s111)
        
        s += "+-----+\n"
        return s
    
class Placar:
    def __init__(self):
        self.posicoes = 10
        self.placar = [0] * self.posicoes
        self.taken = [False] * self.posicoes

    def add(self, posicao, dados):
        if self.taken[posicao - 1]:
            raise ValueError("Posição ocupada")
        if posicao < 7:
            self.placar[posicao - 1] = posicao * dados.count(posicao)
        elif posicao == 7: # Full Hand
            if self.checkFull(dados):
                self.placar[posicao - 1] = 15
        elif posicao == 8: # Sequencia
            if self.checkSeqMaior(dados):
                self.placar[posicao - 1] = 20
        elif posicao == 9:
            if self.checkQuadra(dados):
                self.placar[posicao - 1] = 30
        elif posicao == 10:
            if self.checkQuina(dados):
                self.placar[posicao - 1] = 40
        else:
            raise ValueError("Valor da posição ilegal")
        
        self.taken[posicao - 1] = True

    def getScore(self):
        return sum(p for p, t in zip(self.placar, self.taken) if t)

    def checkFull(self, dados):
        counts = Counter(dados).values()
        return (2 in counts) and (3 in counts)
    
    def checkQuadra(self, dados):
        counts = Counter(dados).values()
        return 4 in counts    

    def checkQuina(self, dados):
        counts = Counter(dados).values()
        return 5 in counts  
    
    def checkSeqMaior(self, dados):
        v = sorted(dados)
        return all(v[i] == v[i + 1] - 1 for i in range(len(v) - 1))
    
    def __str__(self):
        s = ""
        for i in range(3):
            # Primeira coluna (posições 1-3)
            num = f" {self.placar[i]:<3}" if self.taken[i] else f"({i+1}) "
            s += num + "   |   "
            
            # Segunda coluna (posições 7-9)
            num = f" {self.placar[i+6]:<3}" if self.taken[i+6] else f"({i+7}) "
            s += num + "   |  "
            
            # Terceira coluna (posições 4-6)
            num = f" {self.placar[i+3]:<3}" if self.taken[i+3] else f"({i+4}) "
            s += num + "\n-------|----------|-------\n"
        
        # Última linha (posição 10)
        num = f" {self.placar[9]:<3}" if self.taken[9] else "(10)"
        s += "       |   " + num + "   |"
        s += "\n       +----------+\n"
        return s
    
class RolaDados:
    def __init__(self, n, seed):
        rand = random.Random(seed) if seed else random.Random()
        self.dados = [Dado(seed=rand.randint(1, 10000)) if seed != 0 else Dado() for _ in range(n)]

    def rolar(self, quais=None):
        r = []
        for i in range(len(self.dados)):
            if quais is None or str(i + 1) in list(quais):
                self.dados[i].rolar()
            r.append(self.dados[i].getLado())

        return r
    
    def __str__(self):
        s = ""
        for i in range(5):
            base = i * 8
            for d in self.dados:
                p = d.__str__()
                s += p[base : base + 7]
                s += "    "
            s+= "\n"
        return s

if __name__ == "__main__":
    NRODADAS = 10
    seed = int(input("Digite a semente (zero para aleatório): "))
    rd = RolaDados(5, seed)
    pl = Placar()
    print(pl)

    for rodada in range(NRODADAS):
        print("****** Rodada " + str(rodada+1))
        input("Pressione ENTER para lançar os dados\n")

        # Primeira tentativa
        rd.rolar()
        print("1          2          3          4          5")
        print(rd)

        # Segunda tentativa
        muda = input("Digite os números dos dados que quiser TROCAR. Separados por espaços.\n")
        rd.rolar(muda)
        print("1          2          3          4          5")
        print(rd)

        # Terceira tentativa
        muda = input("Digite os números dos dados que quiser TROCAR. Separados por espaços.\n")
        values = rd.rolar(muda)
        print("1          2          3          4          5")
        print(rd)

        # Escolher placar
        print("\n\n")
        print(pl)
        pos = 0
        while pos <= 0:
            try:
                pos = int(input("Escolha a posição que quer ocupar com essa jogada ===> "))  # Converter para int
                if pos > NRODADAS or pos <= 0:
                    pos = 0
                    print("Valor inválido. Posição ocupada ou inexistente.")
                else:
                    pl.add(pos, values)
            except Exception as e:
                pos = 0
                print("Valor inválido. Posição ocupada ou inexistente.")
            
        
        print("\n\n");
        print(pl);

    print("***********************************")
    print("***")
    print("*** Seu escore final foi: " + str(pl.getScore()))
    print("***")
    print("***********************************")
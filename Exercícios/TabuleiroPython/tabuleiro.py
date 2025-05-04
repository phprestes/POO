class Tabuleiro:
    strlinha = "+------"

    def __init__(self, nums, tamanho):
        self.tabuleiro = []
        self.tamanho = tamanho
        self.pos_zero = [0, 0]

        for i in range(tamanho):
            self.tabuleiro.append(nums[i*tamanho : (i+1)*tamanho])
            if 0 in self.tabuleiro[i]:
                self.pos_zero = [i, self.tabuleiro[i].index(0)]
                self.tabuleiro[i][self.tabuleiro[i].index(0)] = ' '

        self.printar()

    def mover(self, cmd):
        movimentos = {
            'u': (1, 0),  # Cima
            'd': (-1, 0),    # Baixo
            'l': (0, 1),   # Esquerda
            'r': (0, -1)     # Direita
        }

        if cmd in movimentos:
            di, dj = movimentos[cmd]
            i, j = self.pos_zero
            ni, nj = i + di, j + dj
            
            if 0 <= ni < self.tamanho and 0 <= nj < self.tamanho:
                # Troca a posição do zero com o elemento adjacente
                self.tabuleiro[i][j], self.tabuleiro[ni][nj] = self.tabuleiro[ni][nj], self.tabuleiro[i][j]
                self.pos_zero = [ni, nj]

        self.printar()

    def checar(self):
        return all(
            (self.tabuleiro[i][j] == i * self.tamanho + j) or 
            ((i, j) == tuple(self.pos_zero))
            for i in range(self.tamanho)
            for j in range(self.tamanho)
        )

    def printar(self):
        # Linha horizontal
        linha_separadora = '+' + ('-' * 6 + '+') * self.tamanho
        
        for i in range(self.tamanho):
            print(linha_separadora)
            for j in range(self.tamanho):
                print(f'| {self.tabuleiro[i][j]:3}  ', end='')
            print('|')  # Fecha a linha
        
        print(linha_separadora + '\n')  # Linha final

if __name__ == "__main__":
    entrada_nums = input();
    entrada_cmds = input();

    nums = [int(num) for num in entrada_nums.split()]
    jogo = Tabuleiro(nums, int(len(nums) ** 0.5))

    for cmd in entrada_cmds:
        jogo.mover(cmd)

    print("Posicao final: " + str(jogo.checar()))
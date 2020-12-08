package prakticheskoe_zadanie_4;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    static final int SIZE = 5;//переменная, которая отвечает за размер игрового поля
    static final int SKOLKO_SYMBOLOV_DLYA_POBEDI = 4;//переменная, которая отвечает за то, сколько нужно крестиков или ноликов подряд для победы

    static final char[][] map = new char[SIZE][SIZE];//массив игрового поля

    static final char DOT_EMPTY = '•';
    static final char DOT_HUMAN = 'X';
    static final char DOT_AI = '0';

    static Scanner in = new Scanner(System.in);

    static final Random random = new Random();

    static final char HEADER_FIRST_SYMBOL = '♥';
    static final char LEVII_VERXNII_UGOL = 9556;
    static final char HORIZ_LINE = 9552;
    static final char PRAVII_VERXNII_UGOL = 9559;
    static final char VERTICAL_LINE = 9553;
    static final char LEVII_NIJNII_UGOL = 9562;
    static final char PRAVII_NIJNII_UGOL = 9565;
    static final String EMPTY = " ";

    public static int SLOJNOST_IGRI;//1 - сложность игры, когда компьютер выбирает свой ход случайно, самая маленькая сложность
    //2 - компьютер смотрит на ход, куда походил человек и случайно делает свой ход, но при этом обязательно рядом с ходом человека
    //3 - компьютер смотрит на ход, куда походил человек, смотрит нет-ли через клетку по горизонтали, по вертикали и по диагонали ещё хода человека и, если есть, то между этими ходами делает свой ход
    //всё это супер-пупер-отлично и я планировал сделать ещё
    public static int KRAINII_HOD_HUMANA_PO_HORIZ;//Крайний ход человека по горизонтали
    public static int KRAINII_HOD_HUMANA_PO_VERTIK;//Крайний ход человека по вертикали

    public static void main(String[] args) {
        privetstvie_programmi();
        turnGame();
    }

    private static void privetstvie_programmi() {
        System.out.println("\nПрактическое задание по уроку № 4");
        System.out.println("---===Игра Крестики-Нолики===----");
        System.out.println("Вы можете выбрать сложность игры от 1 до 5-ти, где 1 - просто, 5 - сложно");
        System.out.println("Сделал Иван Булкин");
        System.out.println();
        TicTacToe.SLOJNOST_IGRI = -100;
        do {
            TicTacToe.SLOJNOST_IGRI = vvod_tselogo_chisla(1, 3, "Выберите сложность игры");
            if (TicTacToe.SLOJNOST_IGRI != -100) {
            } else {
                System.out.print("Вы ошиблись. ");
            }
        }
        while (TicTacToe.SLOJNOST_IGRI == -100);

        System.out.println();
    }

    public static void turnGame() {
        init_massiv_igrovogo_polya();//Инициализируем массив игрового поля и заполнение его пустым значением

        vivod_igrivogo_polya_na_ekran();//Вывод на экран игрового поля, выводится пустое поле

        playGame();//
    }

    private static void playGame() {
        int kol_vo_hodov_vsego = SIZE * SIZE;
        do {
            humanTurn();//ход человека
            vivod_igrivogo_polya_na_ekran();//вывод на экран игрового поля
            if (checkEnd(DOT_HUMAN) == true) {//проверка на окончание игры после хода человека
                System.out.println("Человек победил");
                if (!vixod_iz_programmi()) {//надо будет привыкнуть к такому написанию
                    privetstvie_programmi();
                    turnGame();
                }
            }
            kol_vo_hodov_vsego--;//проверка ничьи нужна и после хода человека и после хода ИИ, т.к. не известно, кто будет ходить первым(почему первым должен ходить человек?)
            if (kol_vo_hodov_vsego == 0) {
                System.out.println("\nСлучилась ничья после хода человека.");
                if (vixod_iz_programmi() == false) {
                    privetstvie_programmi();
                    turnGame();
                }
            }
            ;
            System.out.println("ИИ походил:");
            aiTurn();//ход ИИ
            vivod_igrivogo_polya_na_ekran();//вывод на экран игрового поля
            if (checkEnd(DOT_AI) == true) {//проверка на окончание игры после хода ИИ
                System.out.println("ИИ победил");
                if (vixod_iz_programmi() == false) {
                    privetstvie_programmi();
                    turnGame();
                }
            }
            kol_vo_hodov_vsego--;//проверка ничьи нужна и после хода человека и после хода ИИ, т.к. не известно, кто будет ходить первым(почему первым должен ходить человек?)
            if (kol_vo_hodov_vsego == 0) {
                System.out.println("\nСлучилась ничья после хода ИИ.");
                if (!vixod_iz_programmi()) {//надо будет привыкнуть к такому написанию
                    privetstvie_programmi();
                    turnGame();
                }
            }
            ;
        } while (true);
    }


    //процедура ввода человеком позиции хода по горизонтали
    private static int vvod_horiz_pozitsii_xoda() {
        System.out.printf("Введите позицию Вашего хода по горизонтали, число от 1 до " + SIZE + ": ");
        int vvedennoe_chislo = -100;
        if (in.hasNextInt()) {
            vvedennoe_chislo = in.nextInt();
            if (vvedennoe_chislo < 1 || vvedennoe_chislo > SIZE) {
                vvedennoe_chislo = -100;
            }
        } else {
            in.next();
        }
        return vvedennoe_chislo;
    }

    //процедура ввода человеком позиции хода по вертикали
    private static int vvod_vertic_pozitsii_xoda() {
        System.out.printf("Введите позицию Вашего хода по вертикали, число от 1 до " + SIZE + ": ");
        int vvedennoe_chislo = -100;
        if (in.hasNextInt()) {
            vvedennoe_chislo = in.nextInt();
            if (vvedennoe_chislo < 1 || vvedennoe_chislo > SIZE) {
                vvedennoe_chislo = -100;
            }
        } else {
            in.next();
        }
        return vvedennoe_chislo;
    }

    //Инициализируем массив игрового поля и заполнение его пустым значением
    private static void init_massiv_igrovogo_polya() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    //Вывод на экран игрового поля
    //пробовал убрать вертикальные межстрочные пробелы и ещё как-то улучшить внешний вид вывода игрового поля на экран, но с наскоку ничего не вышло. Оставил всё, как есть
    //Все выводы пустых символов сделаны, чтобы красиво выводить на экран игровое поле размером до 10х10
    private static void vivod_igrivogo_polya_na_ekran() {
        System.out.print(EMPTY);
        System.out.print(HEADER_FIRST_SYMBOL);
        System.out.print(EMPTY);
        System.out.print(EMPTY);
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1);
            if (i < 9) {
                System.out.print(EMPTY);
            }
        }
        System.out.println();
        System.out.print(EMPTY);
        System.out.print(EMPTY);
        System.out.print(LEVII_VERXNII_UGOL);
        for (int i = 0; i < SIZE + SIZE + 1; i++) {
            System.out.print(HORIZ_LINE);
        }
        System.out.print(PRAVII_VERXNII_UGOL);
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            if (i < 9) {
                System.out.print(EMPTY);
            }
            System.out.print(i + 1);
            System.out.print(VERTICAL_LINE);
            System.out.print(EMPTY);
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j]);
                System.out.print(EMPTY);
            }
            System.out.print(VERTICAL_LINE);
            System.out.println();
        }
        System.out.print(EMPTY);
        System.out.print(EMPTY);
        System.out.print(LEVII_NIJNII_UGOL);
        for (int i = 0; i < SIZE + SIZE + 1; i++) {
            System.out.print(HORIZ_LINE);
        }
        System.out.print(PRAVII_NIJNII_UGOL);
        System.out.println();
    }

    //Процедура хода человека
    private static void humanTurn() {
        int rowNumber = -100;
        do {
            rowNumber = vvod_horiz_pozitsii_xoda();
            if (rowNumber != -100) {

            } else {
                System.out.print("Вы ошиблись, ");
            }
        }
        while (rowNumber == -100);

        int columnNumber = -100;
        do {
            columnNumber = vvod_vertic_pozitsii_xoda();
            if (columnNumber != -100) {

            } else {
                System.out.print("Вы ошиблись, ");
            }
        }
        while (columnNumber == -100);
        rowNumber--;//уменьшаем на единицу, т.к. пользователь вводит число от 1 до SIZE, а в массиве всё хранится от 0
        columnNumber--;//уменьшаем на единицу, т.к. пользователь вводит число от 1 до SIZE, а в массиве всё хранится от 0
        if (map[columnNumber][rowNumber] != DOT_EMPTY) {
            rowNumber++;//увеличиваем на единицу, чтобы показать правильные координаты пользователю
            columnNumber++;//увеличиваем на единицу, чтобы показать правильные координаты пользователю
            System.out.printf("Поле " + rowNumber + "/" + columnNumber + " уже занято, введите другие значения Вашего хода по горизонтали и вертикали");
            System.out.println();
            humanTurn();
        } else {
            TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ = columnNumber;//Крайний ход человека по горизонтали, сохраняем, чтобы использовать для создания хода ИИ
            TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK = rowNumber;//Крайний ход человека по вертикали, сохраняем, чтобы использовать для создания хода ИИ
            map[columnNumber][rowNumber] = DOT_HUMAN;
        }//columnNumber - это номер столбца(значение по горизонтали) rowNumber - это номер строки(значение по вертикали)
    }

    //Процедура проверки на окончание игры
    private static boolean checkEnd(char Human_or_AI) {
        boolean isEnd = false;
        int kol_vo_dlya_pobedi = 0;
        //проверяем победу
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
//                System.out.println("F ");//это для тестирования
                kol_vo_dlya_pobedi = 0;
                if (map[i][j] == Human_or_AI) {
                    for (int i1 = i; i1 < SIZE; i1++) {//проверяем нет-ли линии по вертикали
                        if (map[i1][j] == Human_or_AI) {
                            kol_vo_dlya_pobedi++;
                            if (kol_vo_dlya_pobedi == SKOLKO_SYMBOLOV_DLYA_POBEDI) {
                                isEnd = true;
                                System.out.println("\nПОБЕДА по вертикали");
                                return isEnd;
                            }
                        } else {
                            break;//не идём дальше проверять, если соседний символ пустой или не принадлежит человеку/ии
                        }
                    }
                    kol_vo_dlya_pobedi = 0;
                    for (int j1 = j; j1 < SIZE; j1++) {//проверяем нет-ли линии по горизонтали
                        if (map[i][j1] == Human_or_AI) {
                            kol_vo_dlya_pobedi++;
                            if (kol_vo_dlya_pobedi == SKOLKO_SYMBOLOV_DLYA_POBEDI) {
                                isEnd = true;
                                System.out.println("\nПОБЕДА по горизонтали");
                                return isEnd;
                            }
                        } else {
                            break;//не идём дальше проверять, если соседний символ пустой или не принадлежит человеку/ии
                        }
                    }
                    kol_vo_dlya_pobedi = 0;
                    for (int i1 = i; i1 < SIZE; i1++) {//проверяем нет-ли линии по диагонали слева/направо
                        if (map[i1][i1] == Human_or_AI) {
                            kol_vo_dlya_pobedi++;
                            if (kol_vo_dlya_pobedi == SKOLKO_SYMBOLOV_DLYA_POBEDI) {
                                isEnd = true;
                                System.out.println("\nПОБЕДА по диагонали слева/направо");
                                return isEnd;
                            }
                        } else {
                            break;//не идём дальше проверять, если соседний символ пустой или не принадлежит человеку/ии(не делаем лишних вычислений)
                        }
                    }
                    kol_vo_dlya_pobedi = 0;
                    int j1 = j;
                    for (int i1 = i; i1 < SIZE; i1++) {//проверяем нет-ли линии по диагонали справа/налево
//                        System.out.println(i1 + "/" + j1);//это для тестирования
                        if (map[i1][j1] == Human_or_AI) {
                            kol_vo_dlya_pobedi++;
                            if (kol_vo_dlya_pobedi == SKOLKO_SYMBOLOV_DLYA_POBEDI) {
                                isEnd = true;
                                System.out.println("\nПОБЕДА по диагонали справа/налево");
                                return isEnd;
                            }
                        } else {
                            break;//не идём дальше проверять, если соседний символ пустой или не принадлежит человеку/ии(не делаем лишних вычислений)
                        }
                        j1--;
                        if (j1 < 0) {
                            break;
                        }//прерываем цикл, чтобы не было ошибки проверки массива
                    }
                }
            }
        }
        return isEnd;
    }

    //Процедура хода ИИ
    //1 - сложность игры, когда компьютер выбирает свой ход случайно, самая маленькая сложность
    //2 - компьютер смотрит на ход, куда походил человек и случайно делает свой ход, но при этом обязательно рядом с ходом человека
    //3 - компьютер смотрит на ход, куда походил человек, смотрит нет-ли через клетку по горизонтали, по вертикали и по диагонали ещё хода человека и, если есть, то между этими ходами делает свой ход
    private static void aiTurn() {
        int rowNumber = -100;
        int columnNumber = -100;

        if (TicTacToe.SLOJNOST_IGRI == 1) {
            do {
                rowNumber = random.nextInt(SIZE);
                columnNumber = random.nextInt(SIZE);
//            System.out.println("Компьютер ходит: " + rowNumber + "/" + columnNumber);//это для тестирования
            } while (map[columnNumber][rowNumber] != DOT_EMPTY);
            map[columnNumber][rowNumber] = DOT_AI;
        }
        if (TicTacToe.SLOJNOST_IGRI == 2) {
            //Всего может быть восемь различных позиций, куда может пойти ИИ после хода человека и при этом ход ИИ будет рядом с ходом человека
            //создаём массив с возможными ходами ИИ и потом случайным образом из них выбираем куда пойдёт ИИ, для этого надо проверить, что клеточки рядом с ходом человека пусты
            int[][] vozmojnie_hodi_ii = new int[9][2];
            int skoloko_vozmojnix_hodov_ii = 0;
//            TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ--;//Вычитаем их хода человека единицу, т.к. массив начинается с нуля
//            TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK--;//Вычитаем их хода человека единицу, т.к. массив начинается с нуля
            //минус один по горизонтали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус один по горизонтали");//Это для тестирования
                }
            }
            //плюс один по горизонтали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс один по горизонтали");//Это для тестирования
                }
            }
            //минус один по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус один по вертикали");//Это для тестирования
                }
            }
            //плюс один по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс один по вертикали");//Это для тестирования
                }
            }
            //минус один по горизонтали и по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1 >= 0 && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус один по горизонтали и по вертикали");//Это для тестирования
                }
            }
            //плюс один по горизонтали и по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1 < SIZE && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс один по горизонтали и по вертикали");//Это для тестирования
                }
            }
            //плюс один по горизонтали и минус один по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1 < SIZE && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс один по горизонтали и минус один по вертикали");//Это для тестирования
                }
            }
            //минус один по горизонтали и плюс один по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1 >= 0 && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус один по горизонтали и плюс один по вертикали");//Это для тестирования
                }
            }
//            //Это для тестирования
//            for (int i = 0; i < 8; i++) {
//                System.out.println(i + " " + vozmojnie_hodi_ii[i][0] + " " + vozmojnie_hodi_ii[i][1]);
//            }
//            System.out.println("Возможных вариантов: " + skoloko_vozmojnix_hodov_ii);//Это для тестирования
            //Теперь из возможных ходов для ИИ выбираем случайным образом ход
            //На тот случай, если нет возможных вариантов хода, ищем свободную клетку и ходим в неё
            if (skoloko_vozmojnix_hodov_ii == 0) {
                do {
                    rowNumber = random.nextInt(SIZE);
                    columnNumber = random.nextInt(SIZE);
//            System.out.println("Компьютер ходит: " + rowNumber + "/" + columnNumber);//это для тестирования
                } while (map[columnNumber][rowNumber] != DOT_EMPTY);
                map[columnNumber][rowNumber] = DOT_AI;
            } else {
                int hod_ii = (int) (Math.random() * skoloko_vozmojnix_hodov_ii);
                map[vozmojnie_hodi_ii[hod_ii][0]][vozmojnie_hodi_ii[hod_ii][1]] = DOT_AI;
            }
        }
        if (TicTacToe.SLOJNOST_IGRI == 3) {
            //Всего может быть восемь различных позиций, куда может пойти ИИ после хода человека и при этом ход ИИ будет между ходами человека
            //создаём массив с возможными ходами ИИ и потом случайным образом из них выбираем куда пойдёт ИИ, проверяем, что через клетку от хода человека есть другой ход человека
            //но ещё надо проверить не стоит-ли уже между ходами человека ход ИИ
            int[][] vozmojnie_hodi_ii = new int[9][2];
            int skoloko_vozmojnix_hodov_ii = 0;
            //минус два по горизонтали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 2 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 2][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус два по горизонтали");//Это для тестирования
                }
            }
            //плюс два по горизонтали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 2 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 2][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс два по горизонтали");//Это для тестирования
                }
            }
            //минус два по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 2 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 2] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус два по вертикали");//Это для тестирования
                }
            }
            //плюс два по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 2 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 2] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс два по вертикали");//Это для тестирования
                }
            }
            //минус два по горизонтали и по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 2 >= 0 && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 2 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 2][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 2] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус два по горизонтали и по вертикали");//Это для тестирования
                }
            }
            //плюс два по горизонтали и по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 2 < SIZE && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 2 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 2][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 2] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс два по горизонтали и по вертикали");//Это для тестирования
                }
            }
            //плюс два по горизонтали и минус два по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 2 < SIZE && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 2 >= 0) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 2][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 2] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ + 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK - 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("плюс два по горизонтали и минус два по вертикали");//Это для тестирования
                }
            }
            //минус два по горизонтали и плюс два по вертикали
            if (TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 2 >= 0 && TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 2 < SIZE) {
                if (map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 2][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 2] == DOT_HUMAN && map[TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1][TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1] == DOT_EMPTY) {
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][0] = TicTacToe.KRAINII_HOD_HUMANA_PO_HORIZ - 1;
                    vozmojnie_hodi_ii[skoloko_vozmojnix_hodov_ii][1] = TicTacToe.KRAINII_HOD_HUMANA_PO_VERTIK + 1;
                    skoloko_vozmojnix_hodov_ii++;
//                    System.out.println("минус два по горизонтали и плюс два по вертикали");//Это для тестирования
                }
            }
/*            //Это для тестирования
            for (int i = 0; i < 8; i++) {
                System.out.println(i + " " + vozmojnie_hodi_ii[i][0] + " " + vozmojnie_hodi_ii[i][1]);
            }
            System.out.println("Возможных вариантов: " + skoloko_vozmojnix_hodov_ii);//Это для тестирования*/
            //Теперь из возможных ходов для ИИ выбираем случайным образом ход
            //На тот случай, если нет возможных вариантов хода, ищем свободную клетку и ходим в неё
            if (skoloko_vozmojnix_hodov_ii == 0) {
                do {
                    rowNumber = random.nextInt(SIZE);
                    columnNumber = random.nextInt(SIZE);
//            System.out.println("Компьютер ходит: " + rowNumber + "/" + columnNumber);//это для тестирования
                } while (map[columnNumber][rowNumber] != DOT_EMPTY);
                map[columnNumber][rowNumber] = DOT_AI;
            } else {
                int hod_ii = (int) (Math.random() * skoloko_vozmojnix_hodov_ii);
                map[vozmojnie_hodi_ii[hod_ii][0]][vozmojnie_hodi_ii[hod_ii][1]] = DOT_AI;
            }
        }
    }

    //Универсальная процедура выхода из программы, буду её использовать в других программах
    //Выходит из программы, если пользователь выбирает "y", "yes", "д", "да", "+", "торжественно подтверждаю"
    //Возвращает false, если пользователь выбирает "n", "н", "-", "играть, так играть, продолжаем"
    //продолжает спрашивать о выходе, если пользователь ввёл любое другое значение
    private static boolean vixod_iz_programmi() {
        System.out.println("\nВыходим из программы, Вы уверены? y/n (д/н)");
        String n = in.next();
        switch (n) {
            case "y", "yes", "д", "да", "+", "торжественно подтверждаю" -> {
                System.out.println("\nДо новых встреч, ждём Вас снова.");
                in.close();//Необходимо закрыть объект in
                System.exit(0);
            }
            case "n", "н", "-", "играть, так играть, продолжаем" -> {
                System.out.println();
                return false;
            }
            default -> {
                System.out.printf("Вы ввели: " + n + ", такого значения нет в списке%n" + "Выберите y/n или д/н и попробуйте ещё разик.");
                vixod_iz_programmi();
            }
        }
        return false;
    }

    //Универсальная процедура ввода целого числа, буду её использовать в других программах
    //На вход процедуры передаём от какого до какого числа пользовательо должен ввести число
    //min_znachenie - меньше этого числа вводить нельзя
    //max_znachenie - больше этого числа вводить нельзя
    //возвращает -100, если число введено не верно и возвращает целое число, если число введено верно и в нужном интервале
    //message - сообщение пользователю о том, что именно надо вводить
    private static int vvod_tselogo_chisla(int min_znachenie, int max_znachenie, String message) {
        System.out.print(message + ", введите число от " + min_znachenie + " до " + max_znachenie + ": ");
        int vvedennoe_chislo = -100;
        if (in.hasNextInt()) {
            vvedennoe_chislo = in.nextInt();
            if (vvedennoe_chislo < min_znachenie || vvedennoe_chislo > max_znachenie) {
                vvedennoe_chislo = -100;
            }
        } else {
            in.next();
        }
        return vvedennoe_chislo;
    }
}

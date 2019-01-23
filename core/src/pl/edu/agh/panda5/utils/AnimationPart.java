package pl.edu.agh.panda5.utils;

public enum AnimationPart {

    PANDA_WALK_1(0, 0, 42, 66, 0.15f),
    PANDA_WALK_2(45, 0, 40, 66, 0.15f),
    PANDA_WALK_3(85, 0, 48, 66, 0.15f),
    PANDA_WALK_4(134, 0, 40, 66, 0.15f),

    PANDA_JUMP_1(0, 139, 45, 66, 0.1f),
    PANDA_JUMP_2(48, 140, 40, 70, 0.4f),
    PANDA_JUMP_3(88, 140, 45, 68, 999.0f),

    COIN_GOLD_1(65, 62, 80, 80, 0.1f),
    COIN_GOLD_2(160, 62, 80, 80, 0.1f),
    COIN_GOLD_3(265, 62, 80, 80, 0.1f),
    COIN_GOLD_4(350, 62, 80, 80, 0.1f),
    COIN_GOLD_5(412, 62, 64, 80, 0.1f),
    COIN_GOLD_6(468, 62, 80, 80, 0.1f),
    COIN_GOLD_7(550, 62, 80, 80, 0.1f),
    COIN_GOLD_8(630, 62, 80, 80, 0.1f),
    
    COIN_SILVER_1(65, 228, 80, 80, 0.1f),
    COIN_SILVER_2(160, 228, 80, 80, 0.1f),
    COIN_SILVER_3(265, 228, 80, 80, 0.1f),
    COIN_SILVER_4(350, 228, 80, 80, 0.1f),
    COIN_SILVER_5(412, 228, 64, 80, 0.1f),
    COIN_SILVER_6(468, 228, 80, 80, 0.1f),
    COIN_SILVER_7(550, 228, 80, 80, 0.1f),
    COIN_SILVER_8(630, 228, 80, 80, 0.1f),

    COIN_BRONZE_1(65, 388, 80, 80, 0.1f),
    COIN_BRONZE_2(160, 388, 80, 80, 0.1f),
    COIN_BRONZE_3(265, 388, 80, 80, 0.1f),
    COIN_BRONZE_4(350, 388, 80, 80, 0.1f),
    COIN_BRONZE_5(412, 388, 64, 80, 0.1f),
    COIN_BRONZE_6(468, 388, 80, 80, 0.1f),
    COIN_BRONZE_7(550, 388, 80, 80, 0.1f),
    COIN_BRONZE_8(630, 388, 80, 80, 0.1f),

    POWER_UP_MAGNET(305, 156, 86, 113, 999.0f),
    POWER_UP_SHIELD(40, 120, 30, 30, 999.0f),
    POWER_UP_BONUS_JUMP(0, 160, 30, 30, 999.0f),

    BOMB_HUNTER_IDLE(7, 0, 25, 31, 999.0f),
    BOMB(0, 0, 58, 15, 999.0f),

    BOMB_HUNTER_ATTACK_1(6, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_2(38, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_3(70, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_4(105, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_5(137, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_6(170, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_7(200, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_8(230, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_9(263, 97, 25, 31, 0.1f),
    BOMB_HUNTER_ATTACK_10(295, 97, 25, 31, 0.1f),


    ARROW_HUNTER_IDLE(7, 0, 25, 31, 999),
    ARROW_HUNTER_ATTACK_1(6, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_2(38, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_3(70, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_4(105, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_5(137, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_6(170, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_7(200, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_8(230, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_9(263, 97, 25, 31, 0.1f),
    ARROW_HUNTER_ATTACK_10(295, 97, 25, 31, 0.1f);

    static {
        PANDA_WALK_1.nextPart = PANDA_WALK_2;
        PANDA_WALK_2.nextPart = PANDA_WALK_3;
        PANDA_WALK_3.nextPart = PANDA_WALK_4;
        PANDA_WALK_4.nextPart = PANDA_WALK_1;

        PANDA_JUMP_1.nextPart = PANDA_JUMP_2;
        PANDA_JUMP_2.nextPart = PANDA_JUMP_3;
        PANDA_JUMP_3.nextPart = PANDA_JUMP_3;

        COIN_GOLD_1.nextPart = COIN_GOLD_2;
        COIN_GOLD_2.nextPart = COIN_GOLD_3;
        COIN_GOLD_3.nextPart = COIN_GOLD_4;
        COIN_GOLD_4.nextPart = COIN_GOLD_5;
        COIN_GOLD_5.nextPart = COIN_GOLD_6;
        COIN_GOLD_6.nextPart = COIN_GOLD_7;
        COIN_GOLD_7.nextPart = COIN_GOLD_8;
        COIN_GOLD_8.nextPart = COIN_GOLD_1;
        
        COIN_SILVER_1.nextPart = COIN_SILVER_2;
        COIN_SILVER_2.nextPart = COIN_SILVER_3;
        COIN_SILVER_3.nextPart = COIN_SILVER_4;
        COIN_SILVER_4.nextPart = COIN_SILVER_5;
        COIN_SILVER_5.nextPart = COIN_SILVER_6;
        COIN_SILVER_6.nextPart = COIN_SILVER_7;
        COIN_SILVER_7.nextPart = COIN_SILVER_8;
        COIN_SILVER_8.nextPart = COIN_SILVER_1;
        
        COIN_BRONZE_1.nextPart = COIN_BRONZE_2;
        COIN_BRONZE_2.nextPart = COIN_BRONZE_3;
        COIN_BRONZE_3.nextPart = COIN_BRONZE_4;
        COIN_BRONZE_4.nextPart = COIN_BRONZE_5;
        COIN_BRONZE_5.nextPart = COIN_BRONZE_6;
        COIN_BRONZE_6.nextPart = COIN_BRONZE_7;
        COIN_BRONZE_7.nextPart = COIN_BRONZE_8;
        COIN_BRONZE_8.nextPart = COIN_BRONZE_1;

        POWER_UP_MAGNET.nextPart = POWER_UP_MAGNET;
        POWER_UP_BONUS_JUMP.nextPart = POWER_UP_BONUS_JUMP;
        POWER_UP_SHIELD.nextPart = POWER_UP_SHIELD;

        BOMB_HUNTER_IDLE.nextPart = BOMB_HUNTER_IDLE;

        BOMB_HUNTER_ATTACK_1.nextPart = BOMB_HUNTER_ATTACK_2;
        BOMB_HUNTER_ATTACK_2.nextPart = BOMB_HUNTER_ATTACK_3;
        BOMB_HUNTER_ATTACK_3.nextPart = BOMB_HUNTER_ATTACK_4;
        BOMB_HUNTER_ATTACK_4.nextPart = BOMB_HUNTER_ATTACK_5;
        BOMB_HUNTER_ATTACK_5.nextPart = BOMB_HUNTER_ATTACK_6;
        BOMB_HUNTER_ATTACK_6.nextPart = BOMB_HUNTER_ATTACK_7;
        BOMB_HUNTER_ATTACK_7.nextPart = BOMB_HUNTER_ATTACK_8;
        BOMB_HUNTER_ATTACK_8.nextPart = BOMB_HUNTER_ATTACK_9;
        BOMB_HUNTER_ATTACK_9.nextPart = BOMB_HUNTER_ATTACK_10;
        BOMB_HUNTER_ATTACK_10.nextPart = BOMB_HUNTER_IDLE;

        ARROW_HUNTER_IDLE.nextPart = ARROW_HUNTER_IDLE;

        ARROW_HUNTER_ATTACK_1.nextPart = ARROW_HUNTER_ATTACK_2;
        ARROW_HUNTER_ATTACK_2.nextPart = ARROW_HUNTER_ATTACK_3;
        ARROW_HUNTER_ATTACK_3.nextPart = ARROW_HUNTER_ATTACK_4;
        ARROW_HUNTER_ATTACK_4.nextPart = ARROW_HUNTER_ATTACK_5;
        ARROW_HUNTER_ATTACK_5.nextPart = ARROW_HUNTER_ATTACK_6;
        ARROW_HUNTER_ATTACK_6.nextPart = ARROW_HUNTER_ATTACK_7;
        ARROW_HUNTER_ATTACK_7.nextPart = ARROW_HUNTER_ATTACK_8;
        ARROW_HUNTER_ATTACK_8.nextPart = ARROW_HUNTER_ATTACK_9;
        ARROW_HUNTER_ATTACK_9.nextPart = ARROW_HUNTER_ATTACK_10;
        ARROW_HUNTER_ATTACK_10.nextPart = ARROW_HUNTER_IDLE;
    }

    AnimationPart(int x, int y, int width, int height, float timeout) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.timeout = timeout;
    }

    public int x;
    public int y;
    public int w;
    public int h;
    public float timeout;
    public AnimationPart nextPart;


}

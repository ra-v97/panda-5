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
    COIN_BRONZE_8(630, 388, 80, 80, 0.1f);

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

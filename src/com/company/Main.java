package com.company;

import java.util.Random;

public class Main {

    public static int roundNumber = 1;
    public static int bossHealth = 2000;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static String[] heroesAttackType = {
            "Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static double[] heroesHealth = {260, 270, 250, 300, 400, 150, 280, 200};
    public static int[] heroesDamage = {15, 20, 25, 0, 5, 20, 25, 20};

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefence);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0
                && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void round() {
        System.out.println("ROUND: " + roundNumber);
        bossIsOK();
        chooseBossDefence();
        bossHits();
        berserksHitBlock();
        luckysDodge();
        golemReduce();
        heroesHit();
        thorsStunning();
        medicsHeal();
        printStatistics();
        roundNumber++;
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(11); // 0,1,2,3,4,5,6,7,8
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical Damage "
                            + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        if (bossStunned == false) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("============== STATS ==============");
        System.out.println("Boss Health: " + bossHealth + "; Boss Damage: " + bossDamage + "; Stunned: " + bossStunned);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(
                    heroesAttackType[i] + " Health: " + heroesHealth[i] + "; " + heroesAttackType[i] + " Damage: " + heroesDamage[i]);
        }
        System.out.println("===================================");
    }

    // Хилка медика
    public static void medicsHeal() {
        int healMin = 10;
        int healMax = 80;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] < 100 && i != 3 && heroesHealth[i] >= 1 && heroesHealth[3] >= 1) {
                int randHeal = healMin + (int) (Math.random() * ((healMax - healMin) + 1));
                heroesHealth[i] = heroesHealth[i] + randHeal;

                System.out.println("============ SUPPORT ============");
                System.out.println("Medic heals someone: +" + randHeal + " HP");
                System.out.println("=================================");
                break;
            }
        }
    }

    // Задание на сообразительность

    // Герой Golem
    public static void golemReduce() {
        if (bossStunned == false) {
            if (heroesHealth[4] > 0) {
                for (int i = 0; i < heroesHealth.length; i++) {
                    heroesHealth[i] = heroesHealth[i] + (bossDamage * 0.2);
                    if (golemDontReduceMe == true) {
                        heroesHealth[5] = heroesHealth[5] - (bossDamage * 0.2);
                        golemDontReduceMe = false;
                    }
                }
                heroesHealth[4] = heroesHealth[4] - ((bossDamage * 0.2) * 2);
            }
        }
    }

    // Герой Lucky
    public static boolean golemDontReduceMe = false; // Чтобы Golem не защищал Lucky после его уклона

    public static void luckysDodge() {
        int luckMin = 0;
        int luckMax = 100;
        int luckysLuck = luckMin + (int) (Math.random() * ((luckMax - luckMin) + 1));

        if (bossStunned == false) {
            if (luckysLuck > 70) { // Он уклоняется с вероятностью 30% (наверное)
                golemDontReduceMe = true;

                heroesHealth[5] = heroesHealth[5] + bossDamage;
                System.out.println("========== DODGE ==========");
                System.out.println("Lucky dodged his attack!");
                System.out.println("===========================");
            }
        }
    }

    // Герой Berserk
    public static void berserksHitBlock() {
        int blockMin = 0;
        int blockMax = 30;
        int hitBlock = blockMin + (int) (Math.random() * ((blockMax - blockMin) + 1));

        if (bossStunned == false) {
            if (hitBlock > 0) {
                heroesHealth[6] = heroesHealth[6] + hitBlock;
                heroesDamage[6] = heroesDamage[6] + hitBlock;
                System.out.println("============= HIT BLOCK =============");
                System.out.println("Berserk blocked " + hitBlock + " damage and deals " + hitBlock + " more damage to boss");
                System.out.println("=====================================");
                heroesDamage[6] = 25;
            } else {
                System.out.println("============= HIT BLOCK =============");
                System.out.println("bruh. Berserk didn't block his damage");
                System.out.println("=====================================");
            }
        }
    }

    // Герой Thor
    public static boolean bossStunned = false;

    public static void thorsStunning() {
        Random random = new Random();
        boolean canStun = random.nextBoolean();

        if (canStun == true) {
            stunnedForRounds = 2;
            bossStunned = true;

            System.out.println("============== STUNNED ==============");
            System.out.println("Boss stunned by Thor for one round!");
            System.out.println("=====================================");
        }
    }

    // Приношение босса в порядок
    public static int stunnedForRounds = 2;

    public static void bossIsOK() {
        if (bossStunned == true) {
            stunnedForRounds--;
        }
        if (stunnedForRounds == 0) {
            bossStunned = false;

            System.out.println(" ");
            System.out.println("======== THE BOSS IS BACK! ========");
            System.out.println(" ");

            stunnedForRounds = 2;
        }
    }
}

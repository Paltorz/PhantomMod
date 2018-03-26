package juggermod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

import java.util.ArrayList;

public class ImmortalIllusionPower extends AbstractPower{
    public static final String POWER_ID = "Immortal Illusion";
    public static final String NAME = "Immortal Illusion";
    public static final String ONETURN = " turn, take ";
    public static final String[] DESCRIPTIONS = new String[]{
            "After ",
            " turns, take ",
            " damage. NL \n"
    };

    ArrayList<Integer> array = new ArrayList<>();

    public ImmortalIllusionPower(AbstractCreature owner, int countdown, int dmg) {
        this.name = NAME;
        this.ID = "Immortal Illusion";
        this.owner = owner;
        this.amount = 1;
        this.priority = 201;
        this.array.add(countdown);
        this.array.add(dmg);
        this.updateDescription();
        this.img = JuggerMod.getCombatTrainingPowerTexture();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (int i = 0; i < array.size(); i+=2) {
            if (array.get(i) == 1){
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new DamageAction( AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, array.get(i+1), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                array.remove(i+1);
                array.remove(i);
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Immortal Illusion", 1));
                i -= 2;
            }else{
                array.set(i, array.get(i)-1);
            }
        }
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        String str = String.valueOf(stackAmount);
        this.fontScale = 8.0f;
        this.amount += 1;
        this.array.add(Character.getNumericValue(str.charAt(0)));
        str = str.substring(1);
        this.array.add(Integer.parseInt(str));

    }


    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size(); i += 2) {
            sb.append(DESCRIPTIONS[0]);
            sb.append(array.get(i));
            if(array.get(i) == 1){
                sb.append(ONETURN);
            }else{
                sb.append(DESCRIPTIONS[1]);
            }
            sb.append(array.get(i + 1));
            sb.append(DESCRIPTIONS[2]);
        }
        this.description = sb.toString();
    }
}

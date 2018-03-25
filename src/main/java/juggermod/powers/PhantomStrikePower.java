package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

import java.util.ArrayList;

public class PhantomStrikePower extends AbstractPower{
    public static final String POWER_ID = "Phantom Strike";
    public static final String NAME = "Phantom Strike";
    public static final String ONETURN = " turn, gain ";
    public static final String[] DESCRIPTIONS = new String[]{
            "After ",
            " turns, gain ",
            " HP. NL \n"
    };

    ArrayList<Integer> array = new ArrayList<>();
    private static final int COUNTDOWN = 3;

    public PhantomStrikePower(AbstractCreature owner, int special) {
        this.name = NAME;
        this.ID = "Phantom Strike";
        this.owner = owner;
        this.amount = 1;
        this.array.add(COUNTDOWN);
        this.array.add(special);
        this.updateDescription();
        this.img = JuggerMod.getCombatTrainingPowerTexture();
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < array.size(); i+=2) {
            if (array.get(i) == 1){
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, array.get(i+1)));
                array.remove(i+1);
                array.remove(i);
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Phantom Strike", 1));
                i -= 2;
            }else{
                array.set(i, array.get(i)-1);
            }
        }
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += 1;
        this.array.add(COUNTDOWN);
        this.array.add(stackAmount);

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

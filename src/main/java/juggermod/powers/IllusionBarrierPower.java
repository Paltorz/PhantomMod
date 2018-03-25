package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

import java.util.ArrayList;

public class IllusionBarrierPower extends AbstractPower{
    public static final String POWER_ID = "Illusion Barrier";
    public static final String NAME = "Illusion Barrier";
    public static final String ONETURN = " turn, lose ";
    public static final String[] DESCRIPTIONS = new String[]{
            "After ",
            " turns, lose ",
            " Block. NL \n"
    };

    ArrayList<Integer> array = new ArrayList<>();
    private static final int COUNTDOWN = 3;

    public IllusionBarrierPower(AbstractCreature owner, int special) {
        this.name = NAME;
        this.ID = "Illusion Barrier";
        this.owner = owner;
        this.amount = 1;
        this.array.add(COUNTDOWN);
        this.array.add(special);
        this.type = AbstractPower.PowerType.DEBUFF;
        this.priority = 200;
        this.updateDescription();
        this.img = JuggerMod.getImpenetrablePowerTexture();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            for (int i = 0; i < array.size(); i += 2) {
                if (array.get(i) == 1) {
                    this.flash();
                    AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this.owner, this.owner, array.get(i + 1)));
                    array.remove(i + 1);
                    array.remove(i);
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Illusion Barrier", 1));
                    i -= 2;
                } else {
                    array.set(i, array.get(i) - 1);
                }
            }
            this.updateDescription();
        }
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

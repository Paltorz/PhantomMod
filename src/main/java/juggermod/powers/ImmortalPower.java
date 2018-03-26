package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

import java.util.LinkedList;
import java.util.Queue;

public class ImmortalPower extends AbstractPower {
    public static final String POWER_ID = "Immortal";
    public static final String NAME = "Immortal";
    public static final String[] DESCRIPTIONS = new String[]{
            "All damage you take this turn is reduced to 0. Any damage you would take is dealt in ",
            " turns."
    };

    Queue<Integer> q = new LinkedList<>();
    private int dmgTaken;

    public ImmortalPower(AbstractCreature owner, int countdown) {
        this.name = NAME;
        this.ID = "Immortal";
        this.owner = owner;
        this.amount = 1;
        this.dmgTaken = 0;
        this.priority = 201;
        this.q.offer(countdown);
        this.updateDescription();
        this.img = JuggerMod.getCombatTrainingPowerTexture();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        this.dmgTaken += damageAmount;
        return 0;
    }

    @Override
    public void atStartOfTurn() {
        String str = Integer.toString(q.element()) + Integer.toString(this.dmgTaken);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ImmortalIllusionPower(AbstractDungeon.player, this.q.remove(), this.dmgTaken), Integer.parseInt(str)));
        this.dmgTaken = 0;
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Immortal", 1));
    }

    @Override
    public void stackPower(int countdown) {
        this.q.offer(countdown);
        this.fontScale = 8.0f;
        this.amount += 1;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.q.element() + DESCRIPTIONS[1];
    }
}

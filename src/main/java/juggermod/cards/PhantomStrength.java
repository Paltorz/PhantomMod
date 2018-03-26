package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.powers.PhantomStrengthPower;

public class PhantomStrength extends CustomCard{
    public static final String ID = "Phantom Strength";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int STRENGTH = 4;
    private static final int STRENGTH_PLUS = 1;
    private static final int STRENGTH_LOSS = 3;
    private static final int POOL = 1;

    public PhantomStrength() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.PHANTOM_STRENGTH), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseMagicNumber = this.magicNumber = STRENGTH;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PhantomStrengthPower(p, STRENGTH_LOSS), STRENGTH_LOSS));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PhantomStrength();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STRENGTH_PLUS);
        }
    }
}

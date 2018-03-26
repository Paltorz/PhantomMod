package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.powers.PhantasmalOpportunityPower;

public class PhantasmalOpportunity extends CustomCard{
    public static final String ID = "Phantasmal Opportunity";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int DRAW = 3;
    private static final int COUNTDOWN = 2;
    private static final int COUNTDOWN_PLUS = 1;
    private static final int POOL = 1;

    public PhantasmalOpportunity() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.PHANTASMAL_OPPORTUNITY), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseMagicNumber = this.magicNumber = COUNTDOWN;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NoDrawPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PhantasmalOpportunityPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PhantasmalOpportunity();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(COUNTDOWN_PLUS);
        }
    }
}

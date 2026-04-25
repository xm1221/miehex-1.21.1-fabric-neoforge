package cn.xm1221.miehex.util;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate;
import at.petrak.hexcasting.api.casting.eval.vm.FrameFinishEval;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ActionUtils {
    public  CastingEnvironment ENVIRONMENT;
    public  CastingImage IMAGE;
    public  SpellContinuation CONT;
    public  ActionUtils(CastingEnvironment env, CastingImage img, SpellContinuation cont){

        ENVIRONMENT = env;
        IMAGE = img;
        CONT =  cont;

    }

    public  @NotNull CastingEnvironment getEnvironment(){
        return ENVIRONMENT;
    }
    public  @NotNull CastingImage getImage(){
        return IMAGE;
    }
    public  @NotNull SpellContinuation getContinuation(){
        return CONT;
    }
    public OperationResult buildStackResult(List<Iota> stack){
         var img = this.IMAGE;
        CastingImage newImage = img.copy(
                stack,
                img.getParenCount(),
                img.getParenthesized(),
                img.getEscapeNext(),
                img.getOpsConsumed() + 1,
                img.getUserData()
        );
        return new OperationResult(newImage,List.of(), this.CONT, HexEvalSounds.NOTHING);
    }

    public OperationResult buildOpResult(List<Iota> list, SpellContinuation cont){
        FrameFinishEval finishEval = FrameFinishEval.INSTANCE;
        SpellList spellList = new SpellList.LList(list);
        FrameEvaluate evaluate = new FrameEvaluate(spellList,true);
        cont.pushFrame(finishEval);
        cont.pushFrame(evaluate);
        return new OperationResult(this.IMAGE, List.of(), cont, HexEvalSounds.HERMES);
    }





}

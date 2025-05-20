package com.habithive.app.databinding;
import com.habithive.app.R;
import com.habithive.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHomeBindingImpl extends FragmentHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textWelcome, 1);
        sViewsWithIds.put(R.id.quoteCard, 2);
        sViewsWithIds.put(R.id.text_quote, 3);
        sViewsWithIds.put(R.id.text_quote_author, 4);
        sViewsWithIds.put(R.id.button_refresh_quote, 5);
        sViewsWithIds.put(R.id.textStatsTitle, 6);
        sViewsWithIds.put(R.id.cardStats, 7);
        sViewsWithIds.put(R.id.textTotalPointsLabel, 8);
        sViewsWithIds.put(R.id.textTotalPoints, 9);
        sViewsWithIds.put(R.id.textCaloriesBurnedLabel, 10);
        sViewsWithIds.put(R.id.textCaloriesBurned, 11);
        sViewsWithIds.put(R.id.textDailyScoreLabel, 12);
        sViewsWithIds.put(R.id.textDailyScore, 13);
        sViewsWithIds.put(R.id.textWeeklyScoreLabel, 14);
        sViewsWithIds.put(R.id.textWeeklyScore, 15);
        sViewsWithIds.put(R.id.cardHabits, 16);
        sViewsWithIds.put(R.id.textHabitsCountLabel, 17);
        sViewsWithIds.put(R.id.textHabitsCount, 18);
        sViewsWithIds.put(R.id.textCompletedHabitsLabel, 19);
        sViewsWithIds.put(R.id.textCompletedHabits, 20);
        sViewsWithIds.put(R.id.textCompletionRate, 21);
        sViewsWithIds.put(R.id.progressCompletion, 22);
        sViewsWithIds.put(R.id.textProgressTitle, 23);
        sViewsWithIds.put(R.id.cardChart, 24);
        sViewsWithIds.put(R.id.chartProgress, 25);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private FragmentHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageButton) bindings[5]
            , (com.google.android.material.card.MaterialCardView) bindings[24]
            , (com.google.android.material.card.MaterialCardView) bindings[16]
            , (com.google.android.material.card.MaterialCardView) bindings[7]
            , (com.github.mikephil.charting.charts.LineChart) bindings[25]
            , (android.widget.ProgressBar) bindings[22]
            , (androidx.cardview.widget.CardView) bindings[2]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[1]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((com.habithive.app.ui.home.HomeViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.habithive.app.ui.home.HomeViewModel ViewModel) {
        this.mViewModel = ViewModel;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}
package com.habithive.app.databinding;
import com.habithive.app.R;
import com.habithive.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityAddGoalBindingImpl extends ActivityAddGoalBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.quoteCard, 2);
        sViewsWithIds.put(R.id.textTitle, 3);
        sViewsWithIds.put(R.id.inputLayoutTitle, 4);
        sViewsWithIds.put(R.id.editTextTitle, 5);
        sViewsWithIds.put(R.id.inputLayoutDescription, 6);
        sViewsWithIds.put(R.id.editTextDescription, 7);
        sViewsWithIds.put(R.id.inputLayoutTargetPoints, 8);
        sViewsWithIds.put(R.id.editTextTargetPoints, 9);
        sViewsWithIds.put(R.id.inputLayoutTargetCalories, 10);
        sViewsWithIds.put(R.id.editTextTargetCalories, 11);
        sViewsWithIds.put(R.id.textDurationLabel, 12);
        sViewsWithIds.put(R.id.spinnerDuration, 13);
        sViewsWithIds.put(R.id.buttonSave, 14);
        sViewsWithIds.put(R.id.progressBar, 15);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAddGoalBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ActivityAddGoalBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[14]
            , (com.google.android.material.textfield.TextInputEditText) bindings[7]
            , (com.google.android.material.textfield.TextInputEditText) bindings[11]
            , (com.google.android.material.textfield.TextInputEditText) bindings[9]
            , (com.google.android.material.textfield.TextInputEditText) bindings[5]
            , (com.google.android.material.textfield.TextInputLayout) bindings[6]
            , (com.google.android.material.textfield.TextInputLayout) bindings[10]
            , (com.google.android.material.textfield.TextInputLayout) bindings[8]
            , (com.google.android.material.textfield.TextInputLayout) bindings[4]
            , (android.widget.ProgressBar) bindings[15]
            , (bindings[2] != null) ? com.habithive.app.databinding.LayoutMotivationalQuoteBinding.bind((android.view.View) bindings[2]) : null
            , (android.widget.Spinner) bindings[13]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[3]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
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
            setViewModel((com.habithive.app.ui.goals.add.AddGoalViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.habithive.app.ui.goals.add.AddGoalViewModel ViewModel) {
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
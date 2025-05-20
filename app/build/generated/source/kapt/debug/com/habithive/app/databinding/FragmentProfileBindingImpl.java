package com.habithive.app.databinding;
import com.habithive.app.R;
import com.habithive.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentProfileBindingImpl extends FragmentProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cardProfile, 1);
        sViewsWithIds.put(R.id.textNameLabel, 2);
        sViewsWithIds.put(R.id.textName, 3);
        sViewsWithIds.put(R.id.textEmailLabel, 4);
        sViewsWithIds.put(R.id.textEmail, 5);
        sViewsWithIds.put(R.id.cardPersonal, 6);
        sViewsWithIds.put(R.id.textPersonalInfoTitle, 7);
        sViewsWithIds.put(R.id.textGenderLabel, 8);
        sViewsWithIds.put(R.id.textGender, 9);
        sViewsWithIds.put(R.id.textHealthLabel, 10);
        sViewsWithIds.put(R.id.textHealth, 11);
        sViewsWithIds.put(R.id.textHeightLabel, 12);
        sViewsWithIds.put(R.id.textHeight, 13);
        sViewsWithIds.put(R.id.textWeightLabel, 14);
        sViewsWithIds.put(R.id.textWeight, 15);
        sViewsWithIds.put(R.id.textDobLabel, 16);
        sViewsWithIds.put(R.id.textDob, 17);
        sViewsWithIds.put(R.id.textAgeLabel, 18);
        sViewsWithIds.put(R.id.textAge, 19);
        sViewsWithIds.put(R.id.textBmiLabel, 20);
        sViewsWithIds.put(R.id.textBmi, 21);
        sViewsWithIds.put(R.id.switchShareGoal, 22);
        sViewsWithIds.put(R.id.buttonEditProfile, 23);
        sViewsWithIds.put(R.id.buttonLogout, 24);
        sViewsWithIds.put(R.id.progressBar, 25);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private FragmentProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[23]
            , (android.widget.Button) bindings[24]
            , (com.google.android.material.card.MaterialCardView) bindings[6]
            , (com.google.android.material.card.MaterialCardView) bindings[1]
            , (android.widget.ProgressBar) bindings[25]
            , (androidx.appcompat.widget.SwitchCompat) bindings[22]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[14]
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
            setViewModel((com.habithive.app.ui.profile.ProfileViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.habithive.app.ui.profile.ProfileViewModel ViewModel) {
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
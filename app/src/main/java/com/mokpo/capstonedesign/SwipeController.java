package com.mokpo.capstonedesign;

import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class SwipeController extends ItemTouchHelper.Callback {

    private static final int SWIPE_LEFT = ItemTouchHelper.LEFT;
    private static final int SWIPE_RIGHT = ItemTouchHelper.RIGHT;
    private RecyclerView recyclerView;
    public SwipeController(RecyclerView recyclerView) {
        super();
        this.recyclerView = recyclerView;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, SWIPE_LEFT | SWIPE_RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 여기서 수정 및 삭제 버튼 이벤트 처리 구현
        int position = viewHolder.getAdapterPosition();
        if (direction == SWIPE_LEFT) {
            Snackbar.make(viewHolder.itemView, "수정", Snackbar.LENGTH_SHORT).show();
            Snackbar.make(viewHolder.itemView, "삭제", Snackbar.LENGTH_SHORT).show();
            recyclerView.getAdapter().notifyItemChanged(position);
        } else if(direction == SWIPE_RIGHT){
            recyclerView.getAdapter().notifyItemChanged(position);
        }

        /*if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() < viewHolder.itemView.getX()) {
                recyclerView.getAdapter().notifyItemChanged(position);
            }
        }*/

        // 아무 곳이나 터치하면 원래 내용을 보여준다.
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                recyclerView.getAdapter().notifyItemChanged(position);
            }
            return true;
        });
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}

package com.apx5.apx5.ui.templates

/**
 * TemplatesAdapter
 */

class TemplatesAdapter
//public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.ScoreViewHolder> {
//    private final StaticsNavigator mStaticsNavigator;
//    private final Context context;
//    private List<RemoteService.PlayItem> items;
//
//    TemplatesAdapter(Context context, StaticsNavigator staticsNavigator) {
//        this.context = context;
//        this.mStaticsNavigator = staticsNavigator;
//    }
//
//    void setItemsAndRefresh(List<RemoteService.PlayItem> items) {
//        this.items = items;
//        notifyDataSetChanged();
//        mStaticsNavigator.showEmptyList(items.size());
//    }
//
//    private RemoteService.PlayItem getItemAt(int position) {
//        return items.get(position);
//    }
//
//    @NonNull
//    @Override
//    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        PlayItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.play_item, parent, false);
//        binding.setViewModel(new TemplatesBindingModel(context, mStaticsNavigator));
//        return new ScoreViewHolder(binding.getRoot(), binding.getViewModel(), mStaticsNavigator);
//    }
//
//    @Override
//    public void onBindViewHolder(final ScoreViewHolder holder, final int position) {
//        if (holder != null) {
//            final RemoteService.PlayItem item = getItemAt(position);
//            holder.loadItem(item);
//
//            ((ScoreViewHolder) holder).bt_expand.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean show = toggleLayoutExpand(!item.expanded, v, ((ScoreViewHolder) holder).lyt_expand_input);
//                    items.get(position).expanded = show;
//                }
//            });
//
//            if (item.expanded){
//                ((ScoreViewHolder) holder).lyt_expand_input.setVisibility(View.VISIBLE);
//            } else {
//                ((ScoreViewHolder) holder).lyt_expand_input.setVisibility(View.GONE);
//            }
//
//            MaterialUtils.toggleArrow(item.expanded, ((ScoreViewHolder) holder).bt_expand, false);
//        }
//    }
//
//    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
//        MaterialUtils.toggleArrow(show, view);
//        if (show) {
//            ViewAnimation.expand(lyt_expand);
//        } else {
//            ViewAnimation.collapse(lyt_expand);
//        }
//        return show;
//    }
//
//
//    @Override
//    public int getItemCount() {
//        if (items == null) {
//            return 0;
//        }
//
//        return items.size();
//    }
//
//    static class ScoreViewHolder extends RecyclerView.ViewHolder {
//        ImageButton bt_expand;
//        LinearLayout lyt_expand_input;
//        TextView tvDbid;
//        EditText etAwayTeamScore;
//        EditText etHomeTeamScore;
//        Button bt_save_input;
//        TextView tv_play_ready;
//        TextView tv_play_on;
//        TextView tv_play_cancel;
//        private final TemplatesBindingModel viewModel;
//        StaticsNavigator navigator;
//
//        ScoreViewHolder(View itemView, TemplatesBindingModel viewModel, final StaticsNavigator navigator) {
//            super(itemView);
//            this.viewModel = viewModel;
//            this.navigator = navigator;
//
//            bt_expand = (ImageButton) itemView.findViewById(R.id.bt_toggle_input);
//            lyt_expand_input = (LinearLayout) itemView.findViewById(R.id.lyt_expand_input);
//            tvDbid = (TextView) itemView.findViewById(R.id.tvDbid);
//            etAwayTeamScore = (EditText) itemView.findViewById(R.id.etAwayTeamScore);
//            etHomeTeamScore = (EditText) itemView.findViewById(R.id.etHomeTeamScore);
//            bt_save_input = (Button) itemView.findViewById(R.id.bt_save_input);
//            tv_play_cancel = (TextView) itemView.findViewById(R.id.tvPlayCancel);
//            tv_play_on = (TextView) itemView.findViewById(R.id.tvPlayOn);
//            tv_play_ready = (TextView) itemView.findViewById(R.id.tvPlayReady);
//
//            /* 저장*/
//            bt_save_input.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String playId = tvDbid.getText().toString();
//                    String awayScore = etAwayTeamScore.getText().toString();
//                    String homeScore = etHomeTeamScore.getText().toString();
//                    modifyScoreItem(playId, awayScore, homeScore);
//                }
//            });
//
//            /* 경기취소*/
//            tv_play_cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String playId = tvDbid.getText().toString();
//                    modifyScoreItem(playId, "999", "999");
//                }
//            });
//
//            /* 경기중*/
//            tv_play_on.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String playId = tvDbid.getText().toString();
//                    modifyScoreItem(playId, "997", "997");
//                }
//            });
//
//            /* 경기대기*/
//            tv_play_ready.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String playId = tvDbid.getText().toString();
//                    modifyScoreItem(playId, "998", "998");
//                }
//            });
//        }
//
//        /* 점수수정*/
//        private void modifyScoreItem(String playId, String awayScore, String homeScore) {
//            navigator.modifyScore(playId, awayScore, homeScore);
//        }
//
//        void loadItem(RemoteService.PlayItem item) {
//            viewModel.loadItem(item);
//        }
//    }
//}

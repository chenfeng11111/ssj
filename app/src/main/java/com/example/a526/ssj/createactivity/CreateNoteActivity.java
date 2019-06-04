package com.example.a526.ssj.createactivity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.a526.ssj.R;
import com.example.a526.ssj.clockservice.ClockUtil;
import com.example.a526.ssj.createactivity.view.ColorPickerView;
import com.example.a526.ssj.createactivity.view.RichEditor;
import com.example.a526.ssj.entity.Clock;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.entity.Note;
import com.example.a526.ssj.notehelper.NoteHelper;
import com.example.a526.ssj.notehelper.NoteUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    /********************View**********************/
    //文本编辑器
    private RichEditor mEditor;
    //加粗按钮
    private ImageView mBold;
    //颜色编辑器
    private TextView mTextColor;
    //显示显示View
    private LinearLayout llColorView;
    //图片按钮
    private TextView mImage;
    //按序号排列（ol）
    private ImageView mListOL;
    //按序号排列（ul）
    private ImageView mListUL;
    //字体下划线
    private ImageView mLean;
    //字体倾斜
    private ImageView mItalic;
    //字体左对齐
    private ImageView mAlignLeft;
    //字体右对齐
    private ImageView mAlignRight;
    //字体居中对齐
    private ImageView mAlignCenter;
    //字体缩进
    private ImageView mIndent;
    //字体较少缩进
    private ImageView mOutdent;
    //字体索引
    private ImageView mBlockquote;
    //字体中划线
    private ImageView mStrikethrough;
    //字体上标
    private ImageView mSuperscript;
    //字体下标
    private ImageView mSubscript;
    /********************boolean开关**********************/
    //是否加粗
    boolean isClickBold = false;
    //是否正在执行动画
    boolean isAnimating = false;
    //是否按ol排序
    boolean isListOl = false;
    //是否按ul排序
    boolean isListUL = false;
    //是否下划线字体
    boolean isTextLean = false;
    //是否下倾斜字体
    boolean isItalic = false;
    //是否左对齐
    boolean isAlignLeft = false;
    //是否右对齐
    boolean isAlignRight = false;
    //是否中对齐
    boolean isAlignCenter = false;
    //是否缩进
    boolean isIndent = false;
    //是否较少缩进
    boolean isOutdent = false;
    //是否索引
    boolean isBlockquote = false;
    //字体中划线
    boolean isStrikethrough = false;
    //字体上标
    boolean isSuperscript = false;
    //字体下标
    boolean isSubscript = false;
    /********************变量**********************/
    //折叠视图的宽高
    private int mFoldedViewMeasureHeight;

    //选择按钮
    private Button mSelect;
    private boolean isCreator;//是否新建
    private String operation = null;
    private Note originNote = null;//getIntent().getParcelableExtra("note")
    private Date relativeData = null;
    private int noteId = -1;
    //动态申请权限
    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static final int REQUEST_PICK_IMAGE = 11101;
    /*************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        operation = getIntent().getStringExtra("operation");
        //判断当前编辑器是新建笔记还是编辑笔记状态
        if(operation == null || operation.equalsIgnoreCase("create"))
        {
            isCreator = true;
            originNote = (Note) getIntent().getSerializableExtra("note");
        }
        else
        {
            isCreator = false;
        }
        initView();
        initClickListener();
    }

    /**
     * 初始化View
     */
    private void initView() {
        initEditor();
        initMenu();
        initColorPicker();
    }

    /**
     * 初始化文本编辑器
     */
    private void initEditor() {
        mEditor = findViewById(R.id.re_main_editor);
        //mEditor.setEditorHeight(400);
        //输入框显示字体的大小
        mEditor.setEditorFontSize(20);
        //输入框显示字体的颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //输入框背景设置
        mEditor.setEditorBackgroundColor(Color.WHITE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //输入框文本padding
        mEditor.setPadding(10, 10, 10, 10);
        //输入提示文本
        mEditor.setPlaceholder("请输入编辑内容");
        //是否允许输入
        //mEditor.setInputEnabled(false);
        //如果是新建，所有图片默认全屏展示
        if(isCreator)
        {
            mEditor.setHtml("</Div><head><style>img{ width:100% !important;}</style></head>");
        }
        //如果当前是编辑状态，需要把原HTML加载到编辑器中
        else{
            mEditor.setHtml(originNote.getContent());
        }
        //文本输入框监听事件
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                Log.d("mEditor", "html文本：" + text);
            }
        });
    }

    /**
     * 初始化颜色选择器
     */
    private void initColorPicker() {
        ColorPickerView right = findViewById(R.id.cpv_main_color);
        right.setOnColorPickerChangeListener(new ColorPickerView.OnColorPickerChangeListener() {
            @Override
            public void onColorChanged(ColorPickerView picker, int color) {
                mTextColor.setBackgroundColor(color);
                mEditor.setTextColor(color);
            }

            @Override
            public void onStartTrackingTouch(ColorPickerView picker) {

            }

            @Override
            public void onStopTrackingTouch(ColorPickerView picker) {

            }
        });
    }

    /**
     * 初始化菜单按钮
     */
    private void initMenu() {
        mBold = findViewById(R.id.button_bold);
        mTextColor = findViewById(R.id.button_text_color);
        llColorView = findViewById(R.id.ll_main_color);
        mImage = findViewById(R.id.button_image);
        mListOL = findViewById(R.id.button_list_ol);
        mListUL = findViewById(R.id.button_list_ul);
        mLean = findViewById(R.id.button_underline);
        mItalic = findViewById(R.id.button_italic);
        mAlignLeft = findViewById(R.id.button_align_left);
        mAlignRight = findViewById(R.id.button_align_right);
        mAlignCenter = findViewById(R.id.button_align_center);
        mIndent = findViewById(R.id.button_indent);
        mOutdent = findViewById(R.id.button_outdent);
        mBlockquote = findViewById(R.id.action_blockquote);
        mStrikethrough = findViewById(R.id.action_strikethrough);
        mSuperscript = findViewById(R.id.action_superscript);
        mSubscript = findViewById(R.id.action_subscript);
        mSelect = findViewById(R.id.btn_create_select);
        getViewMeasureHeight();
    }

    /**
     * 获取控件的高度
     */
    private void getViewMeasureHeight() {
        //获取像素密度
        float mDensity = getResources().getDisplayMetrics().density;
        //获取布局的高度
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        llColorView.measure(w, h);
        int height = llColorView.getMeasuredHeight();
        mFoldedViewMeasureHeight = (int) (mDensity * height + 0.5);
    }

    private void initClickListener() {
        mBold.setOnClickListener(this);
        mTextColor.setOnClickListener(this);
        mImage.setOnClickListener(this);
        mListOL.setOnClickListener(this);
        mListUL.setOnClickListener(this);
        mLean.setOnClickListener(this);
        mItalic.setOnClickListener(this);
        mAlignLeft.setOnClickListener(this);
        mAlignRight.setOnClickListener(this);
        mAlignCenter.setOnClickListener(this);
        mIndent.setOnClickListener(this);
        mOutdent.setOnClickListener(this);
        mBlockquote.setOnClickListener(this);
        mStrikethrough.setOnClickListener(this);
        mSuperscript.setOnClickListener(this);
        mSubscript.setOnClickListener(this);
        mSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_bold) {//字体加粗
            if (isClickBold) {
                mBold.setImageResource(R.mipmap.bold);
            } else {  //加粗
                mBold.setImageResource(R.mipmap.bold_);
            }
            isClickBold = !isClickBold;
            mEditor.setBold();
        } else if (id == R.id.button_text_color) {//设置字体颜色
            //如果动画正在执行,直接return,相当于点击无效了,不会出现当快速点击时,
            // 动画的执行和ImageButton的图标不一致的情况
            if (isAnimating) return;
            //如果动画没在执行,走到这一步就将isAnimating制为true , 防止这次动画还没有执行完毕的
            //情况下,又要执行一次动画,当动画执行完毕后会将isAnimating制为false,这样下次动画又能执行
            isAnimating = true;

            if (llColorView.getVisibility() == View.GONE) {
                //打开动画
                animateOpen(llColorView);
            } else {
                //关闭动画
                animateClose(llColorView);
            }
        } else if (id == R.id.button_image) {/// /插入图片
            //这里的功能需要根据需求实现，通过insertImage传入一个URL或者本地图片路径都可以，这里用户可以自己调用本地相
            //或者拍照获取图片，传图本地图片路径，也可以将本地图片路径上传到服务器（自己的服务器或者免费的七牛服务器），
            //返回在服务端的URL地址，将地址传如即可（我这里传了一张写死的图片URL，如果你插入的图片不现实，请检查你是否添加
            // 网络请求权限<uses-permission android:name="android.permission.INTERNET" />）
            ActivityCompat.requestPermissions(CreateNoteActivity.this, mPermissionList, 100);
            /*
            mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                    "dachshund");*/
        } else if (id == R.id.button_list_ol) {
            if (isListOl) {
                mListOL.setImageResource(R.mipmap.list_ol);
            } else {
                mListOL.setImageResource(R.mipmap.list_ol_);
            }
            isListOl = !isListOl;
            mEditor.setNumbers();
        } else if (id == R.id.button_list_ul) {
            if (isListUL) {
                mListUL.setImageResource(R.mipmap.list_ul);
            } else {
                mListUL.setImageResource(R.mipmap.list_ul_);
            }
            isListUL = !isListUL;
            mEditor.setBullets();
        } else if (id == R.id.button_underline) {
            if (isTextLean) {
                mLean.setImageResource(R.mipmap.underline);
            } else {
                mLean.setImageResource(R.mipmap.underline_);
            }
            isTextLean = !isTextLean;
            mEditor.setUnderline();
        } else if (id == R.id.button_italic) {
            if (isItalic) {
                mItalic.setImageResource(R.mipmap.lean);
            } else {
                mItalic.setImageResource(R.mipmap.lean_);
            }
            isItalic = !isItalic;
            mEditor.setItalic();
        } else if (id == R.id.button_align_left) {
            if (isAlignLeft) {
                mAlignLeft.setImageResource(R.mipmap.align_left);
            } else {
                mAlignLeft.setImageResource(R.mipmap.align_left_);
            }
            isAlignLeft = !isAlignLeft;
            mEditor.setAlignLeft();
        } else if (id == R.id.button_align_right) {
            if (isAlignRight) {
                mAlignRight.setImageResource(R.mipmap.align_right);
            } else {
                mAlignRight.setImageResource(R.mipmap.align_right_);
            }
            isAlignRight = !isAlignRight;
            mEditor.setAlignRight();
        } else if (id == R.id.button_align_center) {
            if (isAlignCenter) {
                mAlignCenter.setImageResource(R.mipmap.align_center);
            } else {
                mAlignCenter.setImageResource(R.mipmap.align_center_);
            }
            isAlignCenter = !isAlignCenter;
            mEditor.setAlignCenter();
        } else if (id == R.id.button_indent) {
            if (isIndent) {
                mIndent.setImageResource(R.mipmap.indent);
            } else {
                mIndent.setImageResource(R.mipmap.indent_);
            }
            isIndent = !isIndent;
            mEditor.setIndent();
        } else if (id == R.id.button_outdent) {
            if (isOutdent) {
                mOutdent.setImageResource(R.mipmap.outdent);
            } else {
                mOutdent.setImageResource(R.mipmap.outdent_);
            }
            isOutdent = !isOutdent;
            mEditor.setOutdent();
        } else if (id == R.id.action_blockquote) {
            if (isBlockquote) {
                mBlockquote.setImageResource(R.mipmap.blockquote);
            } else {
                mBlockquote.setImageResource(R.mipmap.blockquote_);
            }
            isBlockquote = !isBlockquote;
            mEditor.setBlockquote();
        } else if (id == R.id.action_strikethrough) {
            if (isStrikethrough) {
                mStrikethrough.setImageResource(R.mipmap.strikethrough);
            } else {
                mStrikethrough.setImageResource(R.mipmap.strikethrough_);
            }
            isStrikethrough = !isStrikethrough;
            mEditor.setStrikeThrough();
        } else if (id == R.id.action_superscript) {
            if (isSuperscript) {
                mSuperscript.setImageResource(R.mipmap.superscript);
            } else {
                mSuperscript.setImageResource(R.mipmap.superscript_);
            }
            isSuperscript = !isSuperscript;
            mEditor.setSuperscript();
        } else if (id == R.id.action_subscript) {
            if (isSubscript) {
                mSubscript.setImageResource(R.mipmap.subscript);
            } else {
                mSubscript.setImageResource(R.mipmap.subscript_);
            }
            isSubscript = !isSubscript;
            mEditor.setSubscript();
        }
        //H1--H6省略，需要的自己添加
        //此处自定义BUTTON 用于执行编辑完成后的各种功能
        else if (id == R.id.btn_create_select) {
            //弹出选择菜单
            showPopupMenu(mSelect);
        }
    }
    //选择菜单监听
    //弹出选择按钮
    private void showPopupMenu(View view){
        //View当前PopupMenu显示相对View的位置
        PopupMenu mPopupMenu = new PopupMenu(this,view);
        //menu布局
        mPopupMenu.getMenuInflater().inflate(R.menu.note_help_menu,mPopupMenu.getMenu());
        //menu中的item点击事件
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.menu_action_preview:{
                        Intent intent = new Intent(CreateNoteActivity.this,WebDataActivity.class);
                        intent.putExtra("html",mEditor.getHtml());
                        startActivity(intent);
                    }break;
                    case R.id.menu_action_clock:{
                        //关联闹钟
                         final TimePickerView tvTime = new TimePickerView.Builder(CreateNoteActivity.this, new TimePickerView.OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                date = new Date(date.getTime() - 8 * 60 * 60 * 1000);//转换为东八区时间
                                relativeData = date;
                                System.out.println(date);
                              //  Toast.makeText(getApplication(),date.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }).setType(new boolean[]{ true, true, true,true,true,false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                                 .setLabel("年", "月", "日", "时", "分", "")//默认设置为年月日时分秒
                                 .isCenterLabel(false)
                                 .setDividerColor(Color.RED)
                                 .setTextColorCenter(Color.RED)//设置选中项的颜色
                                 .setTextColorOut(Color.BLUE)//设置没有被选中项的颜色
                                 .setSubmitText("存储")
                                 .setCancelText("取消").build();
                        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                         tvTime.setDate(date);
                         tvTime.show();
                    }break;
                    case R.id.menu_action_save:{
                        if(isCreator)
                        {
                            //弹出文件保存对话框
                            AlertDialog.Builder builder =new AlertDialog.Builder(CreateNoteActivity.this);
                            builder.setTitle("请输入保存的文件名");
                            //通过LayoutInflater来加载一个XML的布局文件作为一个View对象
                            View view = LayoutInflater.from(CreateNoteActivity.this).inflate(R.layout.save_file_dialog,null);
                            //设置自定义布局为弹出框的Content
                            builder.setView(view);

                            final EditText mFileName = (EditText) view.findViewById(R.id.save_file_name);

                            mFileName.setText(getIntent().getStringExtra("fileName"));
                            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String fileName = mFileName.getText().toString().trim();
                                    String fileContent = mEditor.getHtml();
                                    //创建文件对象
                                    Note note = new Note();
                                    note.setTitle(fileName);
                                    note.setShare(false);
                                    note.setUpload(false);
                                    int userId = GlobalVariable.getCurrentUserId();
                                    Date curDate = new Date();
                                    note.setUserId(userId);
                                    note.setSaveTime(curDate);
                                    note.setCode(String.valueOf(userId)+curDate);
                                    note.setVersion(0);
                                    //调用保存图片的接口
                                    String transHtml = NoteUtils.savePicAndTransferHtml(getApplicationContext(),fileName,fileContent);
                                    note.setContent(transHtml);
                                    //调用数据库接口
                                    noteId = GlobalVariable.getNoteDatabaseHolder().insertNote(note);
                                    //提示保存状态
                                    if(noteId>0)
                                    {
                                        //关闭当前页面
                                        Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                        else{
                            //保存修改后的文件到本地
                            String transferHtml = NoteUtils.savePicAndTransferHtml(getApplicationContext(),originNote.getTitle(),mEditor.getHtml());
                            originNote.setContent(transferHtml);
                            originNote.setVersion(originNote.getVersion()+1);
                            noteId = originNote.getUserId();
                            //数据库内容修改
                            GlobalVariable.getNoteDatabaseHolder().updateNote(originNote);
                            Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_SHORT).show();
                        }
                        if(relativeData!=null)
                        {
                            //创建一个闹钟
                            Clock clock = new Clock();
                            clock.setRelatedNoteId(noteId);
                            clock.setTime(relativeData);
                            ClockUtil.saveAlarm(getApplicationContext(),clock);
                        }
                    }break;
                    case R.id.menu_action_upload:{
                        if(isCreator)
                        {
                            //上传文件到服务器
                            //弹出文件保存对话框
                            AlertDialog.Builder builder =new AlertDialog.Builder(CreateNoteActivity.this);
                            builder.setTitle("请输入的文件名");
                            //通过LayoutInflater来加载一个XML的布局文件作为一个View对象
                            View view = LayoutInflater.from(CreateNoteActivity.this).inflate(R.layout.save_file_dialog,null);
                            //设置自定义布局为弹出框的Content
                            builder.setView(view);

                            final EditText mFileName = (EditText) view.findViewById(R.id.save_file_name);

                            builder.setPositiveButton("上传", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String fileName = mFileName.getText().toString().trim();
                                    String fileContent = mEditor.getHtml();
                                    final Note note = new Note();
                                    note.setTitle(fileName);
                                    note.setSaveTime(new Date());
                                    note.setUpload(true);
                                    note.setShare(false);
                                    int userId = GlobalVariable.getCurrentUserId();
                                    Date curDate = new Date();
                                    note.setUserId(userId);
                                    note.setSaveTime(curDate);
                                    note.setCode(String.valueOf(userId)+curDate);
                                    note.setVersion(0);
                                    //本地保存
                                    //图片转移
                                    String localTransferHtml = NoteUtils.savePicAndTransferHtml(getApplicationContext(),fileName,fileContent);
                                    System.out.println("图片转移完成");
                                    //数据库存储
                                    note.setContent(localTransferHtml);
                                    //创建
                                    noteId = GlobalVariable.getNoteDatabaseHolder().insertNote(note);
                                    System.out.println("数据库存储完成");
                                    //服务器上传 status 0 开启新的线程
                                    new Thread(new Runnable(){
                                        @Override
                                        public void run(){
                                            //进行访问网络操作
                                            Message msg = Message.obtain();
                                            Bundle data = new Bundle();
                                            String successful = NoteHelper.uploadFileToServer(note,0);
//                         data.putString("successful", successful? "1" : "0");
                                            data.putString("message", successful);  //实际使用的时候使用上一行代码
                                            msg.setData(data);
                                            handler.sendMessage(msg);
                                        }
                                    }
                                    ).start();

                                  //  String result = NoteHelper.uploadFileToServer(note,0);
                                  //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                        else{//修改后上传服务器
                            //保存修改后的文件到本地
                            String transferHtml = NoteUtils.savePicAndTransferHtml(getApplicationContext(),originNote.getTitle(),mEditor.getHtml());
                            originNote.setContent(transferHtml);
                            originNote.setVersion(originNote.getVersion()+1);
                            noteId = originNote.getId();
                            //本地数据库内容修改
                            GlobalVariable.getNoteDatabaseHolder().updateNote(originNote);
                            //服务器更新 status 1
                            new Thread(new Runnable(){
                                @Override
                                public void run(){
                                    //进行访问网络操作
                                    Message msg = Message.obtain();
                                    Bundle data = new Bundle();
                                    String successful = NoteHelper.uploadFileToServer(originNote,0);
//                         data.putString("successful", successful? "1" : "0");
                                    data.putString("message", successful);  //实际使用的时候使用上一行代码
                                    msg.setData(data);
                                    handler.sendMessage(msg);
                                }
                            }
                            ).start();
                        }

                        if(relativeData!=null)
                        {
                            //创建一个闹钟
                            Clock clock = new Clock();
                            clock.setRelatedNoteId(noteId);
                            clock.setTime(relativeData);
                            ClockUtil.saveAlarm(getApplicationContext(),clock);
                        }
                    }break;
                }
                return false;
            }
        });
        mPopupMenu.show();
    }
    /**
     * 开启动画
     *
     * @param view 开启动画的view
     */
    private void animateOpen(LinearLayout view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, mFoldedViewMeasureHeight);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        animator.start();
    }

    /**
     * 关闭动画
     *
     * @param view 关闭动画的view
     */
    private void animateClose(final LinearLayout view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        animator.start();
    }


    /**
     * 创建动画
     *
     * @param view  开启和关闭动画的view
     * @param start view的高度
     * @param end   view的高度
     * @return ValueAnimator对象
     */
    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    //打开相册响应
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case REQUEST_PICK_IMAGE: {
                if (data == null){
                    return;
                }
                Uri uri=data.getData();
                //将图片添加到富文本编辑器显示
                    mEditor.insertImage(uri.toString(),"picture");
            }break;
            default:break;
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            //从data中拿出存的数据
            String val = data.getString("value");
            //将数据进行显示到界面等操作
            String successful = data.getString("message");
            Toast.makeText(CreateNoteActivity.this,successful,Toast.LENGTH_LONG).show();
            finish();
        }
    };
}

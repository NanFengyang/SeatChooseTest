package com.ldm.seatchoosetest.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.ldm.seatchoosetest.OnNewSeatClickListener;
import com.ldm.seatchoosetest.R;
import com.ldm.seatchoosetest.model.CH_seatInfo;

public class SatView extends View {
	Context mContext;
	int x_offset = 0;

	/** ��ͨ״̬ */
	private Bitmap mBitMapSeatNormal = null;
	/** ������ */
	private Bitmap mBitMapSeatLock = null;
	/** ��ѡ�� */
	private Bitmap mBitMapSeatChecked = null;

	/** ����ͼ���� */
	private Canvas mCanvas = null;

	/** �Ƿ���ʾ����ͼ */
	private boolean isShowThumbnail = false;

	/** ÿ����λ�ĸ߶� - 57 */
	private int ss_seat_current_height = 57;
	/** ÿ����λ�Ŀ�� */
	private int ss_seat_current_width = 57;
	/** ��λ֮��ļ�� */
	private int L = 5;
	private double T = 1.0D;

	private double t = -1.0D;
	private double u = 1.0D;
	/** �Ƿ������ */
	private boolean v = false;

	/** ��λ��С�߶� */
	private int ss_seat_min_height = 0;
	/** ��λ���߶� */
	private int ss_seat_max_height = 0;
	/** ��λ��С��� */
	private int ss_seat_min_width = 0;
	/** ��λ����� */
	private int ss_seat_max_width = 0;

	private OnNewSeatClickListener mOnSeatClickListener = null;

	public static double a = 1.0E-006D;
	private int I = 0;
	private int ss_between_offset = 2;
	private int ss_seat_check_size = 50;
	private SSThumView mSSThumView = null;
	private int ss_seat_thum_size_w = 120;
	private int ss_seat_thum_size_h = 90;
	private int ss_seat_rect_line = 2;
	/** ѡ������ͼ */
	private Bitmap mBitMapThumView = null;
	private volatile int V = 1500;
	/** ��߾� */
	private int left_padding = 0;
	/** �ұ߾� */
	private int right_padding = 0;
	/** �ϱ߾� */
	private int top_padding = 0;
	/** �±߾� */
	private int bom_padding = 0;
	/** ����x��ƫ���� */
	private float X_ThumViewYellow = 0.0F;
	/** ����y��ƫ���� */
	private float Y_ThumViewYellow = 0.0F;
	/** ��λ���������ľ��� */
	private int p = 0;
	/** ������λ���붥�˵ľ��� */
	private int seatView_topPadding = 0;
	/** ����view�Ŀ�� */
	private int SeatView_Width = 0;
	/** ����view�ĸ߶� */
	private int SeatView_height = 0;
	/** �ܷ��ƶ� */
	private boolean w = true;

	private boolean first_load_bg = true;
	private int tempX;
	private int tempY;

	GestureDetector mGestureDetector = new GestureDetector(mContext, new SeatGestureListener(this));

	public List<CH_seatInfo> list_CH_seatInfo;
	/**
	 * ��֪�������
	 */
	private int iMaxPay = 0;

	private int Totalrows;
	private int Totalcolumn;

	public SatView(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public SatView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		this.mContext = paramContext;
	}

	public void init(List<CH_seatInfo> list_CH_seatInfo, SSThumView paramSSThumView) {

		this.mSSThumView = paramSSThumView;
		this.list_CH_seatInfo = list_CH_seatInfo;
		Totalrows = this.list_CH_seatInfo.get(this.list_CH_seatInfo.size() - 1).getRaw() + 1;
		Totalcolumn = this.list_CH_seatInfo.get(this.list_CH_seatInfo.size() - 1).getColumn() + 1;
		this.mBitMapSeatNormal = getBitmapFromDrawable((BitmapDrawable) this.mContext
				.getResources().getDrawable(R.drawable.seat_normal));
		this.mBitMapSeatLock = getBitmapFromDrawable((BitmapDrawable) this.mContext.getResources()
				.getDrawable(R.drawable.seat_lock));
		this.mBitMapSeatChecked = getBitmapFromDrawable((BitmapDrawable) this.mContext
				.getResources().getDrawable(R.drawable.seat_checked));

		this.ss_seat_thum_size_w = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.ss_seat_thum_size_w);
		this.ss_seat_thum_size_h = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.ss_seat_thum_size_h);

		this.ss_seat_max_height = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.seat_max_height);
		this.ss_seat_max_width = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.seat_max_width);
		this.ss_seat_min_height = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.seat_min_height);
		this.ss_seat_min_width = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.seat_min_width);
		this.ss_seat_current_height = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.seat_init_height);
		this.ss_seat_current_width = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.seat_init_width);
		this.ss_seat_check_size = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.ss_seat_check_size);// 30dp
		this.ss_between_offset = this.mContext.getResources().getDimensionPixelSize(
				R.dimen.ss_between_offset);// 5dp
		invalidate();
	}

	public static Bitmap getBitmapFromDrawable(BitmapDrawable paramBitmapDrawable) {
		return paramBitmapDrawable.getBitmap();
	}

	/**
	 * 
	 * @param seatNum
	 *            ÿ�ŵ���λ˳���
	 * @param rowNum
	 *            �ź�
	 * @param paramBitmap
	 * @param paramCanvas1
	 * @param paramCanvas2
	 * @param paramPaint
	 */
	private void drawSeatRow(int columnNum, int rowNum, Bitmap paramBitmap, Canvas paramCanvas1,
			Canvas paramCanvas2, Paint paramPaint) {
		if (paramBitmap == null) {// �ߵ�
			paramCanvas1.drawRect(c(columnNum, rowNum), paramPaint);
			if (this.isShowThumbnail) {
				paramCanvas2.drawRect(d(columnNum, rowNum), paramPaint);
			}
		} else {
			paramCanvas1.drawBitmap(paramBitmap, null, c(columnNum, rowNum), paramPaint);
			if (this.isShowThumbnail) {
				paramCanvas2.drawBitmap(paramBitmap, null, d(columnNum, rowNum), paramPaint);
			}
		}
	}

	/**
	 * 
	 * @param columnNum
	 *            ÿ�ŵ���
	 * @param rowNum
	 *            �ź�
	 * @return
	 */
	private Rect c(int columnNum, int rowNum) {
		try {
			Rect localRect = new Rect(this.left_padding + columnNum * this.ss_seat_current_width
					+ this.L, this.top_padding + rowNum * this.ss_seat_current_height + this.L,
					this.left_padding + (columnNum + 1) * this.ss_seat_current_width - this.L,
					this.top_padding + (rowNum + 1) * this.ss_seat_current_height - this.L);
			return localRect;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return new Rect();
	}

	private Rect d(int columnNum, int rowNum) {
		try {
			Rect localRect = new Rect(5 + (int) (this.T * (this.left_padding + columnNum
					* this.ss_seat_current_width + this.L)), 5 + (int) (this.T * (this.top_padding
					+ rowNum * this.ss_seat_current_height + this.L)),
					5 + (int) (this.T * (this.left_padding + (columnNum + 1)
							* this.ss_seat_current_width - this.L)),
					5 + (int) (this.T * (this.top_padding + (rowNum + 1)
							* this.ss_seat_current_height - this.L)));
			return localRect;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return new Rect();
	}

	private Rect e(int paramInt1, int paramInt2) {
		int i1;
		int i3;
		try {
			if (getMeasuredWidth() < this.SeatView_Width) {
				i1 = getMeasuredWidth();
			} else {
				i1 = this.SeatView_Width;
			}
			if (getMeasuredHeight() < this.SeatView_height) {
				i3 = getMeasuredHeight();
			} else {
				i3 = this.SeatView_height;
			}
			return new Rect((int) (5.0D + this.T * paramInt1), (int) (5.0D + this.T * paramInt2),
					(int) (5.0D + this.T * paramInt1 + i1 * this.T), (int) (5.0D + this.T
							* paramInt2 + i3 * this.T));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new Rect();
		}
	}

	@Override
	protected void onDraw(Canvas paramCanvas) {
		super.onDraw(paramCanvas);
		// Log.i("TAG", "onDraw()...");
		if (this.Totalcolumn == 0 || this.Totalrows == 0) {
			return;
		}

		if (this.X_ThumViewYellow + this.SeatView_Width < 0.0f
				|| this.Y_ThumViewYellow + this.SeatView_height < 0.0f) {
			this.X_ThumViewYellow = 0.0f;
			this.Y_ThumViewYellow = 0.0f;
			this.p = 0;
			this.seatView_topPadding = 0;
		}
		Paint localPaint2 = new Paint();
		if (this.ss_seat_current_width != 0 && this.ss_seat_current_height != 0) {

			this.mBitMapThumView = Bitmap.createBitmap(this.ss_seat_thum_size_w,
					this.ss_seat_thum_size_h, Bitmap.Config.ARGB_8888);
			this.mCanvas = new Canvas();
			this.mCanvas.setBitmap(this.mBitMapThumView);
			this.mCanvas.save();

			Paint localPaint1 = new Paint();
			localPaint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			this.mCanvas.drawPaint(localPaint1);

			double d1 = (this.ss_seat_thum_size_w - 10.0D)
					/ (this.ss_seat_current_width * this.Totalcolumn + this.left_padding + this.right_padding); // -
			// v0/v2
			double d2 = (this.ss_seat_thum_size_h - 10.0D)
					/ (this.ss_seat_current_height * this.Totalrows);
			if (d1 <= d2) {
				this.T = d1;
			} else {
				this.T = d2;
			}
			if (this.isShowThumbnail) {
				localPaint2.setColor(-16777216);
				if (first_load_bg) {
					first_load_bg = false;
					tempX = 5 + (int) (this.SeatView_Width * this.T);
					tempY = 5 + (int) (this.SeatView_height * this.T);
				}
				this.mCanvas.drawRect(5.0F, 5.0F, tempX, tempY, localPaint2);
			}
		}

		paramCanvas.translate(this.X_ThumViewYellow, this.Y_ThumViewYellow);
		this.SeatView_Width = this.left_padding + this.ss_seat_current_width * this.Totalcolumn
				+ this.right_padding;
		this.SeatView_height = this.ss_seat_current_height * this.Totalrows;

		this.left_padding = (int) Math.round(this.ss_seat_current_width / 2.0D);
		localPaint2.setTextAlign(Paint.Align.CENTER);
		localPaint2.setAntiAlias(true);
		localPaint2.setColor(-16777216);

		for (CH_seatInfo localSeat : this.list_CH_seatInfo) {// 2344
			// goto5 - 2344
			switch (localSeat.getStatus()) { // 2373
			case -1: // 2401 - �ߵ�
				localPaint2.setColor(0);
				drawSeatRow(localSeat.getColumn(), localSeat.getRaw(), null, paramCanvas,
						this.mCanvas, localPaint2);
				localPaint2.setColor(-16777216);
				break;
			case 0:// ��ѡ
				drawSeatRow(localSeat.getColumn(), localSeat.getRaw(), this.mBitMapSeatNormal,
						paramCanvas, this.mCanvas, localPaint2);
				break;
			case 2:// ����ѡ
				drawSeatRow(localSeat.getColumn(), localSeat.getRaw(), this.mBitMapSeatLock,
						paramCanvas, this.mCanvas, localPaint2);
				break;
			case 1: // 2һ�ѵ����״̬
				drawSeatRow(localSeat.getColumn(), localSeat.getRaw(), this.mBitMapSeatChecked,
						paramCanvas, this.mCanvas, localPaint2);
				break;
			default:
				break;
			}
		}
		// cond_d - 2538
		// ������
		localPaint2.setTextSize(0.4F * this.ss_seat_current_height);
		for (int i2 = 0; i2 <= this.list_CH_seatInfo.get(list_CH_seatInfo.size() - 1).getRaw(); i2++) {
			localPaint2.setColor(-1308622848);
			paramCanvas.drawRect(new Rect((int) Math.abs(this.X_ThumViewYellow), this.top_padding
					+ i2 * this.ss_seat_current_height, (int) Math.abs(this.X_ThumViewYellow)
					+ this.ss_seat_current_width / 2, this.top_padding + (i2 + 1)
					* this.ss_seat_current_height), localPaint2);
			localPaint2.setColor(-1);
			paramCanvas.drawText(list_CH_seatInfo.get(i2).getPosition() + 1 + "",
					(int) Math.abs(this.X_ThumViewYellow) + this.ss_seat_current_width / 2 / 2,
					this.top_padding + i2 * this.ss_seat_current_height
							+ this.ss_seat_current_height / 2 + this.bom_padding / 2, localPaint2);
		}

		if (this.isShowThumbnail) {
			// ������ͼ�Ļ�ɫ��
			localPaint2.setColor(-739328);
			localPaint2.setStyle(Paint.Style.STROKE);
			localPaint2.setStrokeWidth(this.ss_seat_rect_line);
			this.mCanvas
					.drawRect(
							e((int) Math.abs(this.X_ThumViewYellow),
									(int) Math.abs(this.Y_ThumViewYellow)), localPaint2);
			localPaint2.setStyle(Paint.Style.FILL);
			// paramCanvas.restore();
			this.mCanvas.restore();
		}

		if (this.mSSThumView != null) {
			this.mSSThumView.a(mBitMapThumView);
			this.mSSThumView.invalidate();
		}

	}

	public void setXOffset(int x_offset) {
		this.x_offset = x_offset;
	}

	/**
	 * ��ȡ�����ֱ�߾���
	 * 
	 * @param paramMotionEvent
	 * @return
	 */
	private float getTwoPoiniterDistance(MotionEvent paramMotionEvent) {
		float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
		float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
		return FloatMath.sqrt(f1 * f1 + f2 * f2);
	}

	private void setSaly(MotionEvent paramMotionEvent) {
		double d1 = getTwoPoiniterDistance(paramMotionEvent);
		if (this.t < 0.0D) {
			this.t = d1;
		} else {
			try {
				this.u = (d1 / this.t);
				this.t = d1;
				if ((this.v) && (Math.round(this.u * this.ss_seat_current_width) > 0L)
						&& (Math.round(this.u * this.ss_seat_current_height) > 0L)) {
					this.ss_seat_current_width = (int) Math.round(this.u
							* this.ss_seat_current_width);
					this.ss_seat_current_height = (int) Math.round(this.u
							* this.ss_seat_current_height);
					this.left_padding = (int) Math.round(this.ss_seat_current_width / 2.0D);
					this.right_padding = this.left_padding;
					this.L = (int) Math.round(this.u * this.L);
					if (this.L <= 0)
						this.L = 1;
				}
				invalidate();
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}

	}

	/**
	 * new added
	 * 
	 * @return
	 */
	public static int m(SatView mSsView) {
		return mSsView.left_padding;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @param paramInt
	 * @return
	 */
	public static int m(SatView mSsView, int paramInt) {
		mSsView.V = mSsView.V - paramInt;
		return mSsView.V;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int x(SatView mSsView) {
		return mSsView.V;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 */
	public static void y(SatView mSsView) {
		mSsView.a();
	}

	private void a() {
		// postDelayed(new ag(this), 500L);
	}

	/**
	 * ������λ��ͼ�ĵ�Y��ƫ����
	 * 
	 * @param mSsView
	 * @return
	 */
	public static float seatView_Y(SatView mSsView) {
		return mSsView.Y_ThumViewYellow;
	}

	/**
	 * ��ȡ����x��ƫ����
	 * 
	 * @param mSsView
	 * @return
	 */
	public static float seatView_X(SatView mSsView) {
		return mSsView.X_ThumViewYellow;
	}

	/**
	 * ��ȡ����view�ĸ߶�
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int seatViewHeight(SatView mSsView) {
		return mSsView.SeatView_height;
	}

	/**
	 * ��ȡ������λ���붥�˵ľ���
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int t(SatView mSsView) {
		return mSsView.seatView_topPadding;
	}

	/**
	 * ��ȡ����view�Ŀ��
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int seatViewWidth(SatView mSsView) {
		return mSsView.SeatView_Width;
	}

	/**
	 * ��ȡ��λ���������ĺ������
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int getSeatViewRawDistance(SatView mSsView) {
		return mSsView.p;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int q(SatView mSsView) {
		return mSsView.top_padding;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int p(SatView mSsView) {
		return mSsView.Totalrows;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int o(SatView mSsView) {
		return mSsView.right_padding;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int n(SatView mSsView) {
		return mSsView.Totalcolumn;
	}

	/**
	 * �޸Ŀɼ���λ���붥�˵ľ���
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int l(SatView mSsView, int paramInt) {
		mSsView.seatView_topPadding = mSsView.seatView_topPadding + paramInt;
		return mSsView.seatView_topPadding;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int l(SatView mSsView) {
		return mSsView.ss_seat_current_width;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int k(SatView mSsView) {
		return mSsView.ss_seat_current_width;
	}

	/**
	 * �޸���λ���������ĺ������
	 * 
	 * @param mSsView
	 * @param paramInt
	 * @return
	 */
	public static int k(SatView mSsView, int paramInt) {
		mSsView.p = mSsView.p + paramInt;
		return mSsView.p;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int j(SatView mSsView) {
		return mSsView.ss_seat_current_height;
	}

	/**
	 * ���ÿ�����λ���붥�˵ľ���
	 * 
	 * @param mSsView
	 * @param paramInt
	 * @return
	 */
	public static int j(SatView mSsView, int paramInt) {
		mSsView.seatView_topPadding = paramInt;
		return mSsView.seatView_topPadding;
	}

	/**
	 * ������λ���������ĺ������
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int i(SatView mSsView, int paramInt) {
		mSsView.p = paramInt;
		return mSsView.p;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static boolean i(SatView mSsView) {
		return mSsView.isShowThumbnail;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int h(SatView mSsView, int paramInt) {
		return mSsView.SeatView_height;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int h(SatView mSsView) {
		return mSsView.I + 1;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int g(SatView mSsView, int paramInt) {
		return mSsView.SeatView_Width;
	}

	/**
	 * ��ȡ���֧����λ��
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int getImaxPay(SatView mSsView) {
		return mSsView.iMaxPay;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static boolean a(SatView mSsView, boolean param) {
		mSsView.isShowThumbnail = param;
		return mSsView.isShowThumbnail;
	}

	/**
	 * ��������x��ƫ����
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static float a(SatView mSsView, float param) {
		mSsView.X_ThumViewYellow = param;
		return mSsView.X_ThumViewYellow;
	}

	/**
	 * �����ǵڼ���
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static int a(SatView mSsView, int param) {
		return mSsView.a(param);
	}

	/**
	 * �����ǵڼ���
	 * 
	 * @param paramInt
	 * @return
	 */
	private int a(int paramInt) {
		try {
			int i1 = (paramInt + this.p - this.left_padding) / this.ss_seat_current_width;
			return i1;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return -1;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static Rect a(SatView mSsView, int param1, int param2) {
		return mSsView.f(param1, param2);
	}

	private Rect f(int paramInt1, int paramInt2) {
		try {
			int v1 = this.ss_seat_current_width * paramInt1 + this.left_padding - this.p - this.L;
			int v2 = this.ss_seat_current_height * paramInt2 + this.top_padding
					- this.seatView_topPadding - this.L;
			int v3 = (paramInt1 + 1) * this.ss_seat_current_width + this.left_padding - this.p
					+ this.L;
			int v4 = (this.top_padding + 1) * this.ss_seat_current_height + this.top_padding
					- this.seatView_topPadding + this.L;
			return new Rect(v1, v2, v3, v4);
		} catch (Exception e) {
			e.printStackTrace();
			return new Rect();
		}
	}

	/**
	 * �Ƿ�����ƶ��͵��
	 * 
	 * @param mSsView
	 * @return
	 */
	public static boolean a(SatView mSsView) {
		return mSsView.w;
	}

	private int b() {
		return (int) Math.round(this.ss_seat_current_width / this.ss_seat_check_size
				* this.ss_between_offset);
	}

	/**
	 * �޸�����x���
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static float c(SatView mSsView, float param) {
		mSsView.X_ThumViewYellow = mSsView.X_ThumViewYellow - param;
		return mSsView.X_ThumViewYellow;
	}

	/**
	 * ����ÿ����λ�ĸ߶�
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static float c(SatView mSsView, int param) {
		mSsView.ss_seat_current_height = param;
		return mSsView.ss_seat_current_height;
	}

	/**
	 * �޸�����y���ƫ����
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static float d(SatView mSsView, float param) {
		mSsView.Y_ThumViewYellow = mSsView.Y_ThumViewYellow - param;
		return mSsView.Y_ThumViewYellow;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static int d(SatView mSsView, int param) {
		mSsView.ss_seat_current_width = param;
		return mSsView.ss_seat_current_width;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static OnNewSeatClickListener d(SatView mSsView) {
		return mSsView.mOnSeatClickListener;
	}

	/**
	 * ��������y��ƫ����
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static float b(SatView mSsView, float param) {
		mSsView.Y_ThumViewYellow = param;
		return mSsView.Y_ThumViewYellow;
	}

	/**
	 * �����ǵڼ���
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static int b(SatView mSsView, int param) {
		return mSsView.b(param);
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static List<CH_seatInfo> list_SeatInfo(SatView mSsView) {
		return mSsView.list_CH_seatInfo;
	}

	/**
	 * �����ǵڼ���
	 * 
	 * @param paramInt
	 * @return
	 */
	private int b(int paramInt) {
		try {
			int i1 = (paramInt + this.seatView_topPadding - this.top_padding)
					/ this.ss_seat_current_height;
			return i1;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return -1;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static int e(SatView mSsView, int param) {
		mSsView.left_padding = param;
		return mSsView.left_padding;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int e(SatView mSsView) {
		mSsView.I--;
		return mSsView.I;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @return
	 */
	public static int f(SatView mSsView) {
		return mSsView.I;
	}

	/**
	 * new added
	 * 
	 * @param mSsView
	 * @param param
	 * @return
	 */
	public static int f(SatView mSsView, int param) {
		mSsView.right_padding = param;
		return mSsView.right_padding;
	}

	/**
	 * ���ð�ť����¼�
	 * 
	 * @param paramOnSeatClickLinstener
	 */
	public void setOnSeatClickListener(OnNewSeatClickListener paramOnSeatClickLinstener) {
		this.mOnSeatClickListener = paramOnSeatClickLinstener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getPointerCount() == 1) {
			if (this.v) {
				this.v = false;
				this.w = false;
				this.t = -1.0D;
				this.u = 1.0D;
			} else {
				this.w = true;
			}

			// Toast.makeText(mContext, "���㴥��", Toast.LENGTH_SHORT).show();
			while (this.ss_seat_current_width < this.ss_seat_min_width
					|| this.ss_seat_current_height < this.ss_seat_min_height) {
				this.ss_seat_current_width++;
				this.ss_seat_current_height++;
				this.left_padding = (int) Math.round(this.ss_seat_current_width / 2.0D);
				this.right_padding = this.left_padding;
				this.L = b();
				// �������������
				SatView.i(this, 0);
				SatView.a(this, 0.0F);
				SatView.j(this, 0);
				SatView.b(this, 0.0F);
				invalidate();
			}
			while ((this.ss_seat_current_width > this.ss_seat_max_width)
					|| (this.ss_seat_current_height > this.ss_seat_max_height)) {
				this.ss_seat_current_width--;
				this.ss_seat_current_height--;
				this.left_padding = (int) Math.round(this.ss_seat_current_width / 2.0D);
				this.right_padding = this.left_padding;
				this.L = b();
				invalidate();
			}

			// �ƶ�����-����¼�
			this.mGestureDetector.onTouchEvent(event);
		} else {
			// Toast.makeText(mContext, "��㴥��", Toast.LENGTH_SHORT).show();
			this.v = true;
			setSaly(event);

		}

		return true;
	}

}

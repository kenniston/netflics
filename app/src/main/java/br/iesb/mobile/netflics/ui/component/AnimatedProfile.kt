package br.iesb.mobile.netflics.ui.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.toBitmap
import br.iesb.mobile.netflics.R


class AnimatedProfile @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val COUNTER_RADIUS: Float = 27f
    private val IMAGE_MARGIN_PERCENT: Float = 0.3f
    private val MINIMUM_SIZE: Int = 10

    var profileName: String? = ""
        set(value) {
            field = value
            invalidate()
        }

    var profileNameColor: Int = Color.BLACK
    var profileNameStroke: Boolean = false
    var profileNameStrokeColor: Int = Color.WHITE
    var profileNameTextSize: Float = 42f

    var profileImage: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    var profilePlaceholder: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    var profileEditIcon: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    var profileEditIconTint: Int = Color.WHITE
    var profileShowEditIcon: Boolean =  false
        set(value) {
            field = value
            invalidate()
        }

    var profileCounter: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var profileCounterColor : Int = Color.GRAY
    var profileCounterStrokeColor: Int = Color.DKGRAY
    var profileCounterTextColor: Int = Color.WHITE
    var profileCounterStrokeWidth: Float = 1.2f

    var profileStartColor: Int = Color.TRANSPARENT
    var profileCenterColor: Int = Color.TRANSPARENT
    var profileEndColor: Int = Color.TRANSPARENT

    var profileBorderColor: Int = Color.TRANSPARENT
    var profileBorderWidth: Int = 0

    var profileAnimatedCounter: Boolean = false
        set(value) {
            field = value
            if (value) {
                if (counterAnimator.isRunning) {
                    counterAnimator.resume()
                } else {
                    counterAnimator.start()
                }
            } else {
                counterAnimator.pause()
            }
        }
    private var counterRotation: Int = 0
    private val counterAnimator = ValueAnimator.ofInt(0, 360)

    private var p: Paint = Paint()
    private val gradientDrawable: GradientDrawable = GradientDrawable()

    private var onEditClickListener: OnEditClickListener? = null

    init {
        configureCounterAnimation()

        context.withStyledAttributes(attrs, R.styleable.AnimatedProfile) {
            // Profile Name Attributes
            profileName = getString(R.styleable.AnimatedProfile_profileName) ?: ""
            profileNameColor = getColor(
                R.styleable.AnimatedProfile_profileNameColor,
                Color.BLACK
            )
            profileNameStroke = getBoolean(R.styleable.AnimatedProfile_profileNameStroke, false)
            profileNameStrokeColor = getColor(
                R.styleable.AnimatedProfile_profileNameStrokeColor,
                Color.WHITE
            )
            profileNameTextSize = getFloat(R.styleable.AnimatedProfile_profileNameTextSize, 42f)

            // Image and Placeholder Attributes
            profileImage = getDrawable(R.styleable.AnimatedProfile_profileImage)
            profilePlaceholder = getDrawable(R.styleable.AnimatedProfile_profilePlaceholder)

            // Edit Icon
            profileEditIcon = getDrawable(R.styleable.AnimatedProfile_profileEditIcon)
            profileEditIconTint = getInt(
                R.styleable.AnimatedProfile_profileEditIconTint,
                Color.WHITE
            )
            profileShowEditIcon = getBoolean(R.styleable.AnimatedProfile_profileShowEditIcon, false)

            // Counter Attributes
            profileCounter = getInt(R.styleable.AnimatedProfile_profileCounter, 0)
            profileCounterColor = getColor(
                R.styleable.AnimatedProfile_profileCounterColor,
                Color.GRAY
            )
            profileCounterStrokeColor = getColor(
                R.styleable.AnimatedProfile_profileCounterStrokeColor,
                Color.DKGRAY
            )
            profileCounterTextColor = getColor(
                R.styleable.AnimatedProfile_profileCounterTextColor,
                Color.WHITE
            )
            profileCounterStrokeWidth = getFloat(
                R.styleable.AnimatedProfile_profileCounterStrokeWidth,
                1.2f
            )
            profileAnimatedCounter = getBoolean(
                R.styleable.AnimatedProfile_profileAnimatedCounter,
                false
            )

            // Profile Gradient Attributes
            profileStartColor = getColor(
                R.styleable.AnimatedProfile_profileStartColor,
                Color.TRANSPARENT
            )
            profileCenterColor = getColor(
                R.styleable.AnimatedProfile_profileCenterColor,
                Color.TRANSPARENT
            )
            profileEndColor = getColor(
                R.styleable.AnimatedProfile_profileEndColor,
                Color.TRANSPARENT
            )

            // Profile Border
            profileBorderColor = getColor(
                R.styleable.AnimatedProfile_profileBorderColor,
                Color.TRANSPARENT
            )
            profileBorderWidth = getInt(R.styleable.AnimatedProfile_profileBorderWidth, 0)
        }
        p.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.TRANSPARENT)

        drawGradientBackground(canvas)
        drawProfileImage(canvas)
        drawCounter(canvas)
        drawName(canvas)
        drawEditIcon(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val desiredHeight = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(
            MINIMUM_SIZE.coerceAtLeast(desiredWidth),
            MINIMUM_SIZE.coerceAtLeast(desiredHeight)
        )
    }

    private fun drawGradientBackground(canvas: Canvas) {
        gradientDrawable.orientation = GradientDrawable.Orientation.TOP_BOTTOM
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        gradientDrawable.bounds = Rect(0, 0, width, height)
        gradientDrawable.cornerRadii = floatArrayOf(40f, 40f, 40f, 40f, 40f, 40f, 40f, 40f)
        gradientDrawable.setStroke(profileBorderWidth, profileBorderColor)
        gradientDrawable.colors = arrayOf(profileStartColor, profileCenterColor, profileEndColor).toIntArray()
        gradientDrawable.draw(canvas)
    }

    private fun drawCounter(canvas: Canvas) {
        if (profileCounter == 0) return

        canvas.save()

        // Counter background position
        val x = width - COUNTER_RADIUS * 1.8f
        val y = 0 + COUNTER_RADIUS * 1.8f

        canvas.rotate(counterRotation.toFloat(), x, y)

        // Counter text position
        val yt = y + COUNTER_RADIUS.div(2)

        // Draw counter border
        p.color = profileCounterStrokeColor
        p.strokeWidth = profileCounterStrokeWidth
        canvas.drawCircle(x, y, COUNTER_RADIUS * profileCounterStrokeWidth, p)

        // Draw counter circle background
        p.color = profileCounterColor
        canvas.drawCircle(x, y, COUNTER_RADIUS, p)

        // Calculate the font size for the counter
        p.color = profileCounterTextColor
        p.textSize = 36f
        //setTextSizeForWidth(p, COUNTER_RADIUS, profileCounter.toString())
        p.textAlign = Paint.Align.CENTER

        // Draw counter text
        val str = if (profileCounter < 100) profileCounter.toString() else "\uD83D\uDE31"
        canvas.drawText(str, x, yt, p)

        canvas.restore()
    }

    private fun drawProfileImage(canvas: Canvas) {
        val img = (if (profileImage != null) profileImage else profilePlaceholder) ?: return
        val x = (width * (IMAGE_MARGIN_PERCENT / 2))
        val y = (height * (IMAGE_MARGIN_PERCENT / 3f))
        val w = (width - (width * IMAGE_MARGIN_PERCENT)).coerceAtLeast(1f)
        val h = height - (height * IMAGE_MARGIN_PERCENT).coerceAtLeast(1f)
        val b = img.toBitmap(w.toInt(), h.toInt())

        canvas.drawBitmap(b, x, y, Paint())
    }

    private fun drawName(canvas: Canvas) {
        val textPaint = TextPaint()
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isLinearText = true
        textPaint.isFakeBoldText = true
        textPaint.letterSpacing = 0.09f
        textPaint.strokeWidth = 4.5f
        textPaint.strokeJoin = Paint.Join.ROUND
        textPaint.textSize = profileNameTextSize

        val txt = profileName ?: ""
        val str = TextUtils.ellipsize(
            txt,
            textPaint,
            width.toFloat().div(1.2f),
            TextUtils.TruncateAt.END
        )

        val textHeight = textPaint.descent() - textPaint.ascent()
        val textYOffset = (textHeight / 2) - textPaint.descent()

        val x = width.div(2)
        val y = height - (height * (IMAGE_MARGIN_PERCENT / 2.7f)) + textYOffset

        if (profileNameStroke) {
            textPaint.color = profileNameStrokeColor
            textPaint.style = Paint.Style.STROKE
            canvas.drawText(str, 0, str.length, x.toFloat(), y, textPaint)
        }

        textPaint.color = profileNameColor
        textPaint.style = Paint.Style.FILL
        canvas.drawText(str, 0, str.length, x.toFloat(), y, textPaint)
    }

    private fun drawEditIcon(canvas: Canvas) {
        if (!profileShowEditIcon) return
        val img = profileEditIcon ?: return

        val x = 15f
        val y = 18f

        img.colorFilter = PorterDuffColorFilter(profileEditIconTint, PorterDuff.Mode.SRC_IN)
        val b = img.toBitmap(2 * COUNTER_RADIUS.toInt(), 2 * COUNTER_RADIUS.toInt())

        canvas.drawBitmap(b, x, y, p)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.x > 15 &&
                event.x < (15 + 3 * COUNTER_RADIUS.toInt()) &&
                event.y > 18 &&
                event.y < (18 + 3 * COUNTER_RADIUS.toInt()) &&
                profileShowEditIcon
            ) {
                onEditClickListener?.onClick(this)
            } else {
                performClick()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    fun setProfileImage(bmp: Bitmap) {
        profileImage = BitmapDrawable(resources, bmp)
        postInvalidate()
    }

    fun setOnEditClickListener(listener: OnEditClickListener) {
        onEditClickListener = listener
    }

    private fun configureCounterAnimation() {
        counterAnimator.duration = 3000
        counterAnimator.repeatCount = -1
        counterAnimator.repeatMode = ValueAnimator.REVERSE
        counterAnimator.addUpdateListener {
            counterRotation = it.animatedValue as Int
            invalidate()
        }
        counterAnimator.interpolator = AccelerateDecelerateInterpolator()
    }

    /**
     * Interface definition for a callback to be invoked when the edit icon is clicked.
     */
    fun interface OnEditClickListener {
        /**
         * Called when the edit icon has been clicked.
         *
         * @param name The profile name that was clicked.
         */
        fun onClick(v: AnimatedProfile)
    }

}
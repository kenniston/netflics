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
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.toBitmap
import br.iesb.mobile.netflics.R


class AnimatedProfile @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val COUNTER_RADIUS: Float = 27f
    private val IMAGE_MARGIN: Int = 50

    var profileName: String? = null
    var profileNameColor: Int = Color.BLACK
    var profileNameStroke: Boolean = false
    var profileNameStrokeColor: Int = Color.WHITE
    var profileNameTextSize: Float = 42f

    var profileImage: Drawable? = null
    var profilePlaceholder: Drawable? = null

    var profileCounter: Int = 0
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

    init {
        configureCounterAnimation()

        context.withStyledAttributes(attrs, R.styleable.AnimatedProfile) {
            // Profile Name Attributes
            profileName = getString(R.styleable.AnimatedProfile_profileName)
            profileNameColor = getColor(R.styleable.AnimatedProfile_profileNameColor,
                Color.BLACK)
            profileNameStroke = getBoolean(R.styleable.AnimatedProfile_profileNameStroke, false)
            profileNameStrokeColor = getColor(R.styleable.AnimatedProfile_profileNameStrokeColor,
                Color.WHITE)
            profileNameTextSize = getFloat(R.styleable.AnimatedProfile_profileNameTextSize, 42f)

            // Image and Placeholder Attributes
            profileImage = getDrawable(R.styleable.AnimatedProfile_profileImage)
            profilePlaceholder = getDrawable(R.styleable.AnimatedProfile_profilePlaceholder)

            // Counter Attributes
            profileCounter = getInt(R.styleable.AnimatedProfile_profileCounter, 0)
            profileCounterColor = getColor(R.styleable.AnimatedProfile_profileCounterColor,
                Color.GRAY)
            profileCounterStrokeColor = getColor(R.styleable.AnimatedProfile_profileCounterStrokeColor,
                Color.DKGRAY)
            profileCounterTextColor = getColor(R.styleable.AnimatedProfile_profileCounterTextColor,
                Color.WHITE)
            profileCounterStrokeWidth = getFloat(R.styleable.AnimatedProfile_profileCounterStrokeWidth,
                1.2f)
            profileAnimatedCounter = getBoolean(R.styleable.AnimatedProfile_profileAnimatedCounter, false)

            // Profile Gradient Attributes
            profileStartColor = getColor(R.styleable.AnimatedProfile_profileStartColor,
                Color.TRANSPARENT)
            profileCenterColor = getColor(R.styleable.AnimatedProfile_profileCenterColor,
                Color.TRANSPARENT)
            profileEndColor = getColor(R.styleable.AnimatedProfile_profileEndColor,
                Color.TRANSPARENT)

            // Profile Border
            profileBorderColor = getColor(R.styleable.AnimatedProfile_profileBorderColor,
                Color.TRANSPARENT)
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
        val b = img.toBitmap(width - IMAGE_MARGIN * 2, height - IMAGE_MARGIN * 2)

        canvas.drawBitmap(b, IMAGE_MARGIN.toFloat(), IMAGE_MARGIN.div(2).toFloat(), Paint())
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

        val str = TextUtils.ellipsize(profileName, textPaint, width.toFloat().div(1.2f), TextUtils.TruncateAt.END)

        val x = width.div(2)
        val y = height - IMAGE_MARGIN.div(2)

        if (profileNameStroke) {
            textPaint.color = profileNameStrokeColor
            textPaint.style = Paint.Style.STROKE
            canvas.drawText(str, 0, str.length, x.toFloat(), y.toFloat(), textPaint)
        }

        textPaint.color = profileNameColor
        textPaint.style = Paint.Style.FILL
        canvas.drawText(str, 0, str.length, x.toFloat(), y.toFloat(), textPaint)
    }

    private fun setTextSizeForWidth(paint: Paint, desiredWidth: Float, text: String) {
        val testTextSize = 96f
        paint.textSize = testTextSize
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val desiredTextSize = testTextSize * desiredWidth / bounds.width()
        paint.textSize = desiredTextSize
    }

    fun setProfileImage(bmp: Bitmap) {
        profileImage = BitmapDrawable(resources, bmp)
        postInvalidate()
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

}
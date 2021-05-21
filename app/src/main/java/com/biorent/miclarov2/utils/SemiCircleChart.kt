package com.biorent.miclarov2.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.biorent.miclarov2.R

class SemiCircleChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ValueAnimator.AnimatorUpdateListener {

    private lateinit var  animator: ValueAnimator
    private var alto:Int = 0
    private var ancho:Int = 0
    private var currentAngle:Float = 0f
    private var currentPercentage:Float = 0f
    private lateinit var  rectExt: RectF
    private lateinit var rectInter: RectF

    private var startAngleEmpArc=0f
    private var sweepAngleEmpArc = 0f
    private var startAngleGraphArc = 0f
    private var sweepAngleGraphArc= 0f
    private var startAngleCoverArc = 0f
    private var sweepAngleCoverArc=0f
    private var rotateCanvas = 0f

    private lateinit var posColorGradient: FloatArray
    private lateinit var shader: Shader

    private val color25Two = Color.rgb(31, 254, 94)
    private val color50One = Color.rgb(113, 165, 255)
    private val color50Two = Color.rgb(0, 94, 255)
    private val color75One = Color.rgb(255, 158, 92)
    private val color75Two = Color.rgb(255, 104, 0)
    private val color100One =Color.rgb(183, 28, 28)

    private lateinit var  positionText: PointXY
    private lateinit var  centerCanvas: PointXY

    //colores para degradado del grafico
    private var currentCol1:Int=0
    private var currentCol2:Int=0
    //pinceles
    private lateinit var textPorcent: Paint
    private lateinit var emptyArc: Paint
    private lateinit var coverArc: Paint
    private lateinit var graphArc: Paint
    //grueso del grafico en proprocion a la altura
    private val withGraphPorcent = 0.18f //18%
    //tamaño del texto en proporcion a la altura
    private val textSizePorcent = 0.1f //10%

    private var ratio : Float = 2f
    private var orientation : Int = 1
    private var textColor : Int = Color.GRAY
    private var animationDuration : Int = 2000
    private var backColor: Int = Color.WHITE

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SemiCircleChart)
        ratio = a.getFloat(R.styleable.SemiCircleChart_ratio, 2f)
        orientation = a.getInt(R.styleable.SemiCircleChart_orientation, 1)
        backColor = a.getColor(R.styleable.SemiCircleChart_backgroundColor, Color.WHITE)
        textColor = a.getColor(R.styleable.SemiCircleChart_textColor, Color.WHITE)
        animationDuration = a.getInt(R.styleable.SemiCircleChart_animationDuration, 2000)
        a.recycle()
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measuredWidth == 0 && measuredHeight == 0){
            return          }
    }

    private fun initPaint() {
        emptyArc = Paint()
        emptyArc.color = Color.GRAY
        coverArc = Paint()
        coverArc.color = backColor
        graphArc = Paint()
        textPorcent = Paint()
        textPorcent.textAlign = Paint.Align.LEFT
        textPorcent.color = textColor
        val typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        textPorcent.typeface = typeface
    }

    private fun setAnimation(percentage: Float) {
        animator = ValueAnimator()
        animator.duration = animationDuration.toLong()
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener(this)
        animator.setFloatValues(0f, percentage)
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        alto = height
        ancho = width
        canvas.drawColor(backColor)
        canvas.drawArc(rectExt, startAngleEmpArc, sweepAngleEmpArc, true, emptyArc)
        canvas.save()
        canvas.rotate(rotateCanvas, centerCanvas.x, centerCanvas.y)
        canvas.drawArc(rectExt, startAngleGraphArc, sweepAngleGraphArc, true, graphArc)
        canvas.drawArc(rectInter, startAngleCoverArc, sweepAngleCoverArc, true, coverArc)
        canvas.restore()
        canvas.drawText("$currentPercentage%", positionText.x, positionText.y, textPorcent)
    }

    private fun setColorGraph(percentage: Float) {
        if (percentage <= 25) {
            currentCol1 = color50One
            currentCol2 = color50Two
        } else if (25 < percentage && percentage <= 49) {
            currentCol1 = color25Two
            currentCol2 = color25Two
        } else if (49 < percentage && percentage <= 75) {
            currentCol1 = color75One
            currentCol2 = color75Two
        } else if (75 < percentage && percentage <= 100) {
            currentCol1 = color100One
            currentCol2 = Color.rgb(244, 67, 54)
        }
    }

    private fun refreshValues() {
        val colors = intArrayOf(currentCol1, currentCol2)
        val porcionGraph = 0.5f * (100 - currentPercentage) / 100
        var grueso = alto * withGraphPorcent
        textPorcent.textSize = calculateSizeText(alto)
        when (orientation) {
            1 -> {
                alto *= 2
                grueso = ancho * withGraphPorcent
                textPorcent.textSize = calculateSizeText(ancho)
                textPorcent.textAlign = Paint.Align.CENTER
                rectExt = RectF(0f, 0f, ancho.toFloat(), alto.toFloat())
                rectInter = RectF(grueso, grueso, ancho - grueso, alto - grueso)
                positionText = PointXY((ancho / 2).toFloat(), (alto - grueso) / 2)
                centerCanvas = PointXY(0f, 0f)
                rotateCanvas = 0f //no se usan
                startAngleEmpArc = 180f
                sweepAngleEmpArc = 180f
                startAngleGraphArc = 180f
                sweepAngleGraphArc = currentAngle
                startAngleCoverArc = 180f
                sweepAngleCoverArc = 180f
                posColorGradient = floatArrayOf(0.5f, 0.5f + porcionGraph)
                shader = SweepGradient((ancho / 2).toFloat(), (alto / 2).toFloat(), colors, posColorGradient)
            }
            2 -> {
                grueso = ancho * withGraphPorcent
                textPorcent.textSize = calculateSizeText(ancho)
                textPorcent.textAlign = Paint.Align.CENTER
                rectExt = RectF(0f, (-alto / 2).toFloat(), ancho.toFloat(), (alto / 2).toFloat())
                rectInter = RectF(grueso, grueso - alto / 2, ancho - grueso, alto / 2 - grueso)
                positionText = PointXY((ancho / 2).toFloat(), grueso)
                centerCanvas = PointXY(0f, 0f)
                rotateCanvas = 0f //no se usan
                startAngleEmpArc = 180f
                sweepAngleEmpArc = -180f
                startAngleGraphArc = 180f
                sweepAngleGraphArc = -currentAngle
                startAngleCoverArc = 180f
                sweepAngleCoverArc = -180f
                posColorGradient = floatArrayOf(porcionGraph, 0.5f)
                shader = SweepGradient((ancho / 2).toFloat(), 0f, colors, posColorGradient)
            }
            3 -> {
                ancho *= 2
                rectExt = RectF(0f, 0f, ancho.toFloat(), alto.toFloat())
                rectInter = RectF(grueso, grueso, ancho - grueso, alto - grueso)
                positionText = PointXY(grueso + grueso / 5, (alto / 2).toFloat())
                centerCanvas = PointXY(0f, 0f) //no se usa
                startAngleEmpArc = 90f
                sweepAngleEmpArc = 180f
                startAngleGraphArc = -90f
                sweepAngleGraphArc = -currentAngle
                startAngleCoverArc = 90f
                sweepAngleCoverArc = 180f
                rotateCanvas = 0f //no se usa
                posColorGradient = floatArrayOf(0.25f + porcionGraph, 0.75f)
                shader = SweepGradient((ancho / 4).toFloat(), (alto / 2).toFloat(), colors, posColorGradient)
            }
            4 -> {
                ancho  *= 2
                rectExt = RectF((-ancho / 2).toFloat(), 0f, (ancho / 2).toFloat(), alto.toFloat())
                rectInter = RectF(-ancho / 2 + grueso, grueso, ancho / 2 - grueso, alto - grueso)
                positionText = PointXY(grueso / 6, (alto / 2).toFloat())
                centerCanvas = PointXY(0f, (alto / 2).toFloat())
                startAngleEmpArc = -90f
                sweepAngleEmpArc = 180f
                startAngleGraphArc = 90f
                sweepAngleGraphArc = currentAngle
                startAngleCoverArc = 90f
                sweepAngleCoverArc = 180f
                rotateCanvas = 180f
                posColorGradient = floatArrayOf(0.25f, 0.75f - porcionGraph)
                shader = SweepGradient(0f, (alto / 2).toFloat(), colors, posColorGradient)
            }
            else -> {
            }
        }
        graphArc.shader = shader
    }

    private fun calculateSizeText(size: Int): Float {
        return size * textSizePorcent
    }

    fun drawChart(percentage : Float){
        if (percentage in 0.0..100.0) {
            setColorGraph(percentage)
            setAnimation(percentage)
        } else {
            Log.e("SemiCircleChart", "ERROR: Value out of bounds [0-100]")
            setWillNotDraw(true)
        }
    }

    inner class PointXY(var x: Float, var y: Float)

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        currentPercentage = animator.animatedValue as Float
        currentAngle = currentPercentage * 180 / 100
        refreshValues()
        invalidate()
    }
}
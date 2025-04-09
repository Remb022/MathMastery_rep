package com.kynzai.game2048.animation

import android.graphics.Canvas
import com.kynzai.game2048.game.logic.MovementDirection

// Константа длительности анимации
private const val ANIMATION_DURATION = 300f // в миллисекундах

// Наша собственная реализация линейной интерполяции
private fun lerp(start: Float, end: Float, progress: Float): Float {
    return start + (end - start) * progress.coerceIn(0f, 1f)
}

data class TileAnimation(
    val currentRow: Int,
    val currentCol: Int,
    val targetRow: Int,
    val targetCol: Int,
    val animationType: AnimationType, // APPEAR, MERGE, MOVE
    var progress: Float = 0f // 0..1
)

enum class AnimationType {
    APPEAR, MERGE, MOVE
}

class AnimationManager {
    private val animations = mutableListOf<TileAnimation>()
    var isAnimating = false
        private set

    fun addAnimation(animation: TileAnimation) {
        animations.add(animation)
        isAnimating = true
    }

    fun update(deltaTime: Float) {
        if (animations.isEmpty()) {
            isAnimating = false
            return
        }

        val iterator = animations.iterator()
        while (iterator.hasNext()) {
            val anim = iterator.next()
            anim.progress += deltaTime / ANIMATION_DURATION

            if (anim.progress >= 1f) {
                iterator.remove()
            }
        }
    }

    fun getCurrentAnimations(): List<TileAnimation> = animations
}

// Вспомогательные функции для работы с плитками
data class Tile(
    val row: Int,
    val col: Int,
    val value: Int
)

// Функция преобразования клетки в пиксели (замените на вашу реализацию)
private fun cellToPx(cell: Int): Float {
    // TODO:
    return cell * 100f // Примерная реализация
}

// Функции отрисовки (замените на ваши реализации)
private fun drawStaticTile(canvas: Canvas, tile: Tile) {
    // TODO:
}

private fun drawScaledTile(canvas: Canvas, tile: Tile, scale: Float) {
    // TODO:
}

fun drawTile(canvas: Canvas, tile: Tile, animation: TileAnimation?) {
    if (animation == null) {
        drawStaticTile(canvas, tile)
        return
    }

    when (animation.animationType) {
        AnimationType.APPEAR -> {
            val scale = lerp(0f, 1f, animation.progress)
            drawScaledTile(canvas, tile, scale)
        }
        AnimationType.MOVE -> {
            val startX = cellToPx(animation.currentCol)
            val startY = cellToPx(animation.currentRow)
            val endX = cellToPx(animation.targetCol)
            val endY = cellToPx(animation.targetRow)

            val x = lerp(startX, endX, animation.progress)
            val y = lerp(startY, endY, animation.progress)

            canvas.save()
            canvas.translate(x, y)
            drawStaticTile(canvas, tile)
            canvas.restore()
        }
        AnimationType.MERGE -> {
            val scale = if (animation.progress < 0.5f) {
                lerp(1f, 1.2f, animation.progress * 2)
            } else {
                lerp(1.2f, 1f, (animation.progress - 0.5f) * 2)
            }
            drawScaledTile(canvas, tile, scale)
        }
    }
}

fun moveTiles(
    direction: MovementDirection,
    animationManager: AnimationManager,
    moves: List<Pair<Tile, Tile>>,
    newTiles: List<Tile>
) {
    moves.forEach { (from, to) ->
        animationManager.addAnimation(
            TileAnimation(
                currentRow = from.row,
                currentCol = from.col,
                targetRow = to.row,
                targetCol = to.col,
                animationType = if (from.value == to.value) AnimationType.MERGE else AnimationType.MOVE
            )
        )
    }

    newTiles.forEach { tile ->
        animationManager.addAnimation(
            TileAnimation(
                currentRow = tile.row,
                currentCol = tile.col,
                targetRow = tile.row,
                targetCol = tile.col,
                animationType = AnimationType.APPEAR
            )
        )
    }
}
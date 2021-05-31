package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.ModBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.pathfinding.PathType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.Explosion
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World

class SteamPressFrame(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = frameShape
    override fun getInteractionShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = frameShape
    override fun isPathfindable(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun destroy(world: IWorld, pos: BlockPos, state: BlockState) {
        removeSteamPressStructure(world, pos)
    }

    // FIXME only drops when base block breaks
    private fun removeSteamPressStructure(worldIn: IWorld, pos: BlockPos) {
        if (!worldIn.isClientSide) {
            val blockPos1 = pos.below()
            val blockPos2 = pos.above()

            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressBase)
                worldIn.setBlock(blockPos1, Blocks.AIR.defaultBlockState(), 0b100011)
            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressTop)
                worldIn.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 0b100011)
        }
    }

    companion object {
        private val bar1: VoxelShape = box(0.0, 0.0, 0.0, 2.0, 16.0, 2.0)
        private val bar2: VoxelShape = box(14.0, 0.0, 0.0, 16.0, 16.0, 2.0)
        private val bar3: VoxelShape = box(14.0, 0.0, 14.0, 16.0, 16.0, 16.0)
        private val bar4: VoxelShape = box(0.0, 0.0, 14.0, 2.0, 16.0, 16.0)
        val frameShape: VoxelShape = VoxelShapes.or(bar1, bar2, bar3, bar4)
    }
}
